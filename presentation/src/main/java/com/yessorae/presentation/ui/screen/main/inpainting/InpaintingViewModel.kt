package com.yessorae.presentation.ui.screen.main.inpainting

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.yessorae.common.Logger
import com.yessorae.common.bitmapToByteArray
import com.yessorae.domain.model.inpainting.InPaintingRequest
import com.yessorae.domain.model.parameter.Prompt
import com.yessorae.domain.usecase.UploadImageUseCase
import com.yessorae.domain.usecase.inpainting.InsertInPaintingHistoryUseCase
import com.yessorae.domain.usecase.prompt.DeletePromptUseCase
import com.yessorae.domain.usecase.model.GetAllSDModelsUseCase
import com.yessorae.domain.usecase.model.GetFeaturedSDModelsUseCase
import com.yessorae.domain.usecase.prompt.GetNegativePromptsFlowUseCase
import com.yessorae.domain.usecase.prompt.GetPositivePromptsFlowUseCase
import com.yessorae.domain.usecase.prompt.InsertPromptUseCase
import com.yessorae.domain.usecase.model.InsertUsedSDModelUseCase
import com.yessorae.domain.util.StableDiffusionConstants
import com.yessorae.presentation.R
import com.yessorae.presentation.navigation.destination.InPaintingResultDestination
import com.yessorae.presentation.ui.screen.main.inpainting.model.InpaintingDialogState
import com.yessorae.presentation.ui.screen.main.inpainting.model.InPaintingUiState
import com.yessorae.presentation.ui.screen.main.inpainting.model.SegmentationLabel
import com.yessorae.presentation.ui.screen.main.tti.model.PromptOption
import com.yessorae.presentation.ui.screen.main.tti.model.SDModelOption
import com.yessorae.presentation.ui.screen.main.tti.model.SchedulerOption
import com.yessorae.presentation.ui.screen.main.tti.model.asDomainModel
import com.yessorae.presentation.ui.screen.main.tti.model.asOption
import com.yessorae.presentation.util.helper.ImageHelper
import com.yessorae.presentation.util.base.BaseScreenViewModel
import com.yessorae.presentation.util.helper.ImageSegmentationHelper
import com.yessorae.presentation.util.helper.StringResourceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InpaintingViewModel @Inject constructor(
    private val getPositivePromptsFlowUseCase: GetPositivePromptsFlowUseCase,
    private val getNegativePromptsFlowUseCase: GetNegativePromptsFlowUseCase,
    private val getFeaturedSDModelsUseCase: GetFeaturedSDModelsUseCase,
    private val insertPromptUseCase: InsertPromptUseCase,
    private val deletePromptUseCase: DeletePromptUseCase,
    private val getAllSDModelsUseCase: GetAllSDModelsUseCase,
    private val insertUsedSDModelUseCase: InsertUsedSDModelUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val insertInPaintingHistoryUseCase: InsertInPaintingHistoryUseCase,
    private val imageHelper: ImageHelper,
    private val imageSegmentationHelper: ImageSegmentationHelper,
    private val stringResourceHelper: StringResourceHelper
) : BaseScreenViewModel() {
    /** state field */
    private val _uiState = MutableStateFlow<InPaintingUiState>(InPaintingUiState.Initial)
    val uiState: StateFlow<InPaintingUiState> = _uiState.asStateFlow()

    private val _dialog = MutableStateFlow<InpaintingDialogState>(InpaintingDialogState.None)
    val dialogState = _dialog.asStateFlow()

    private val _positivePromptOptions = MutableStateFlow<List<PromptOption>>(listOf())
    val positivePromptOptions = _positivePromptOptions.asStateFlow().onSubscription {
        subscribePositivePrompts()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    private val _negativePromptOptions = MutableStateFlow<List<PromptOption>>(listOf())
    val negativePromptOptions = _negativePromptOptions.asStateFlow().onSubscription {
        subscribeNegativePrompts()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    private val _guidanceScale = MutableStateFlow(StableDiffusionConstants.INITIAL_GUIDANCE_SCALE)
    val guidanceScale = _guidanceScale.asStateFlow()

    private val _featuredSDModelOptions = MutableStateFlow<List<SDModelOption>>(listOf())
    val featuredSdModelOptions = _featuredSDModelOptions.asStateFlow().onSubscription {
        subscribeFeaturedSDModels()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    private val _allSDModelOptions = MutableStateFlow<List<SDModelOption>>(listOf())
    val allSDModelOptions = _allSDModelOptions.asStateFlow().onSubscription {
        getAllSDModels()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf() // TODO:: SR-N check
    )

    private val _schedulerOptions = MutableStateFlow(SchedulerOption.initialScheduler)
    val schedulerOptions = _schedulerOptions.asStateFlow()

    private val _stepCount = MutableStateFlow(StableDiffusionConstants.INITIAL_STEP_COUNT)
    val stepCount = _stepCount.asStateFlow()

    private val _takePicture = MutableSharedFlow<Unit>()
    val takePicture = _takePicture.asSharedFlow()

    private val _pickImage = MutableSharedFlow<Unit>()
    val pickImage = _pickImage.asSharedFlow()

    private val selectedPositivePrompts: List<PromptOption>
        get() {
            return positivePromptOptions.value.filter { it.selected }
        }
    private val selectedNegativePrompts: List<PromptOption>
        get() {
            return negativePromptOptions.value.filter { it.selected }
        }

    private val selectedSDModel: SDModelOption?
        get() {
            return featuredSdModelOptions.value.firstOrNull { it.selected }
        }

    private val selectedScheduler: SchedulerOption?
        get() {
            return schedulerOptions.value.firstOrNull { it.selected }
        }


    /** request, load ..  */
    private fun subscribePositivePrompts() = ioScope.launch {
        getPositivePromptsFlowUseCase().collectLatest { prompts ->
            val selectedPromptSet = selectedPositivePrompts.map { it.prompt }.toSet()
            _positivePromptOptions.update {
                prompts.map { prompt ->
                    val promptOption: PromptOption = prompt.asOption()
                    promptOption.copy(selected = selectedPromptSet.contains(prompt.prompt))
                }
            }
        }
    }

    private fun subscribeNegativePrompts() = ioScope.launch {
        getNegativePromptsFlowUseCase().collectLatest { prompts ->
            val selectedPromptSet = selectedNegativePrompts.map { it.prompt }.toSet()
            _negativePromptOptions.update {
                prompts.map { prompt ->
                    val promptOption: PromptOption = prompt.asOption()
                    promptOption.copy(selected = selectedPromptSet.contains(prompt.prompt))
                }
            }
        }
    }

    private fun subscribeFeaturedSDModels() = ioScope.launch {
        getFeaturedSDModelsUseCase().collectLatest { models ->
            val selectedModel = selectedSDModel
            _featuredSDModelOptions.update {
                models.map { model ->
                    val modelOption = model.asOption()
                    modelOption.copy(selected = selectedModel?.model?.id == modelOption.model.id)
                }
            }
        }
    }

    private fun getAllSDModels() = ioScope.launch {
        val selectedModel = selectedSDModel
        _allSDModelOptions.update {
            getAllSDModelsUseCase().map { model ->
                val modelOption = model.asOption()
                modelOption.copy(selected = selectedModel?.model?.id == modelOption.model.id)
            }
        }
    }

    /** user interaction */
    fun clickAddImage() = viewModelScope.launch {
        _dialog.emit(InpaintingDialogState.ImageAddMethod)
    }

    fun clickTakePicture() = viewModelScope.launch {
        _takePicture.emit(Unit)
    }

    fun clickPickImage() = viewModelScope.launch {
        _pickImage.emit(Unit)
    }

    fun completeTakePicture(bitmap: Bitmap) {
        segmentImage(initialBitmap = bitmap)
        cancelDialog()
    }

    fun completePickImage(uri: Uri) {
        val bitmap = imageHelper.uriToBitmapWithSizeLimit512(uri = uri)
        segmentImage(initialBitmap = bitmap)
        cancelDialog()
    }

    fun clickToAddPositivePrompt() = viewModelScope.launch {
        _dialog.update {
            InpaintingDialogState.PositivePromptAddition
        }
    }

    fun clickToAddNegativePrompt() = viewModelScope.launch {
        _dialog.update {
            InpaintingDialogState.NegativePromptOptionAddition
        }
    }

    fun clickPositivePrompt(clickedOption: PromptOption) {
        _positivePromptOptions.update { list ->
            list.map { option ->
                if (clickedOption.prompt == option.prompt) {
                    option.copy(
                        selected = option.selected.not()
                    )
                } else {
                    option
                }
            }
        }
    }

    fun clickNegativePrompt(clickedOption: PromptOption) {
        _negativePromptOptions.update { list ->
            list.map { option ->
                if (clickedOption.prompt == option.prompt) {
                    option.copy(
                        selected = option.selected.not()
                    )
                } else {
                    option
                }
            }
        }
    }

    fun longClickPrompt(clickedOption: PromptOption) = viewModelScope.launch {
        _dialog.update {
            InpaintingDialogState.PromptDeletionConfirmation(
                title = stringResourceHelper.getString(R.string.common_dialog_button_confirm),
                promptOption = clickedOption
            )
        }
    }

    fun changePromptStrength(guidanceScale: Int) {
        _guidanceScale.update {
            guidanceScale
        }
    }

    fun clickFeaturedSDModel(clickedOption: SDModelOption) {
        _featuredSDModelOptions.update { list ->
            list.map { option ->
                if (clickedOption.model.id == option.model.id) {
                    option.copy(
                        selected = option.selected.not()
                    )
                } else {
                    option.copy(
                        selected = false
                    )
                }
            }
        }
    }

    fun clickMoreSDModel() = viewModelScope.launch {
        _dialog.update {
            InpaintingDialogState.MoreSDModelBottomSheet
        }
    }

    fun clickMoreSDModel(model: SDModelOption) = ioScope.launch {
        insertUsedSDModelUseCase(model.model)
        showToast(message = stringResourceHelper.getString(R.string.common_model_added))
    }


    fun clickSegmentationLabel(label: SegmentationLabel) = viewModelScope.launch {
        (uiState.value as? InPaintingUiState.MaskedImage)?.let { state ->
            val newImage =
                imageSegmentationHelper.mapMPImageToBitmap(mpImage = state.mpImage, label = label)
            _uiState.update {
                state.copy(
                    maskedBitmap = newImage
                )
            }
        }
    }

    fun clickAddPositivePrompt(prompt: String) = ioScope.launch {
        insertPromptUseCase(
            prompt = Prompt.create(
                prompt = prompt,
                positive = true
            )
        )
        cancelDialog()
    }

    fun clickAddNegativePrompt(prompt: String) = ioScope.launch {
        insertPromptUseCase(
            prompt = Prompt.create(
                prompt = prompt,
                positive = false
            )
        )
        cancelDialog()
    }

    fun clickDeletePrompt(prompt: PromptOption) = ioScope.launch {
        deletePromptUseCase(prompt = prompt.asDomainModel())
    }


    fun segmentImage(initialBitmap: Bitmap) = viewModelScope.launch {
        _uiState.update { InPaintingUiState.Loading }

        val result = imageSegmentationHelper.segmentImageFile(
            initialBitmap
        )

        val mpImage = result?.categoryMask()?.get() ?: run {
            _toast.emit(stringResourceHelper.getString(R.string.in_painting_segmentation_error))
            return@launch
        }

        _uiState.update {
            InPaintingUiState.MaskedImage(
                initialBitmap = initialBitmap,
                maskedBitmap = imageSegmentationHelper.mapMPImageToBitmap(
                    mpImage = mpImage,
                    label = SegmentationLabel.ALL
                ),
                mpImage = mpImage
            )
        }
    }

    fun inPaintImage() = viewModelScope.launch {
        // TODO:: SR-N 가공 로직을 UseCase로 올릴지 고민중. 뷰모델 사이즈 줄이고 재사용성 높일 수 있지만 파라미터 수가 부담됨.
        val maskedImageState = uiState.value
        if (maskedImageState !is InPaintingUiState.MaskedImage) return@launch
        val initialByteArrayImage =
            maskedImageState.initialBitmap.bitmapToByteArray() ?: return@launch
        val maskedByteArrayImage =
            maskedImageState.maskedBitmap.bitmapToByteArray() ?: return@launch

        val positivePrompts = selectedPositivePrompts
            .joinToString(StableDiffusionConstants.PROMPT_SEPARATOR) { it.prompt }

        val negativePrompts = selectedNegativePrompts
            .joinToString(StableDiffusionConstants.PROMPT_SEPARATOR) { it.prompt }

        val sdModelId = selectedSDModel?.model?.id

        val schedulerId = selectedScheduler?.scheduler?.id

        if (positivePrompts.isEmpty()) {
            showToast(
                message = stringResourceHelper.getString(
                    resourceId = R.string.common_warning_input_prompt
                )
            )
            return@launch
        }

        if (sdModelId == null) {
            showToast(
                message = stringResourceHelper.getString(
                    resourceId = R.string.common_warning_select_model
                )
            )
            return@launch
        }

        if (schedulerId == null) {
            showToast(
                message = stringResourceHelper.getString(
                    resourceId = R.string.common_warning_select_scheduler
                )
            )
            return@launch
        }


        val initImageUrl = async {
            uploadImageUseCase(image = initialByteArrayImage)
        }

        val maskImageUrl = async {
            uploadImageUseCase(image = maskedByteArrayImage)
        }

        Logger.presentation("width: ${maskedImageState.mpImage.width}, height: ${maskedImageState.mpImage.height}")
        val historyId = insertInPaintingHistoryUseCase(
            request = InPaintingRequest(
                prompt = positivePrompts,
                negativePrompt = negativePrompts,
                initImage = initImageUrl.await(),
                maskImage = maskImageUrl.await(),
                guidanceScale = guidanceScale.value.toDouble(),
                strength = 0.5, // TODO:: SR-N 0.0~1.0 사이의 UI 추가해야함
                modelId = sdModelId,
                width = maskedImageState.mpImage.width,
                height = maskedImageState.mpImage.height,
                scheduler = schedulerId,
                steps = stepCount.value,
            )
        )

        _navigationEvent.emit(
            InPaintingResultDestination.getRouteWithArgs(requestId = historyId.toInt())
        )

    }

    override fun onCleared() {
        super.onCleared()
        imageSegmentationHelper.clearImageSegmenter()
    }

    fun cancelDialog() = viewModelScope.launch {
        _dialog.emit(InpaintingDialogState.None)
    }
}


// TODO:: SR-N Refactoring
fun Int.toAlphaColor(): Int {
    return Color.argb(
        128,
        Color.red(this),
        Color.green(this),
        Color.blue(this)
    )
}

val labelColors = listOf(
    -16777216,
    -8388608,
    -16744448,
    -8355840,
    -16777088,
    -8388480,
    -16744320,
    -8355712,
    -12582912,
    -4194304,
    -12550144,
    -4161536,
    -12582784,
    -4194176,
    -12550016,
    -4161408,
    -16760832,
    -8372224,
    -16728064,
    -8339456,
    -16760704
)