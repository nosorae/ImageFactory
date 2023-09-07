package com.yessorae.imagefactory.ui.screen.main.tti

import android.graphics.Bitmap
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yessorae.common.GaConstants
import com.yessorae.common.GaEventManager
import com.yessorae.common.Logger
import com.yessorae.common.replaceDomain
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequest
import com.yessorae.data.repository.TxtToImgRepository
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.mapper.PromptMapper
import com.yessorae.imagefactory.mapper.PublicModelMapper
import com.yessorae.imagefactory.mapper.TxtToImgResultMapper
import com.yessorae.imagefactory.model.EmbeddingsModelOption
import com.yessorae.imagefactory.model.LoRaModelOption
import com.yessorae.imagefactory.model.PromptOption
import com.yessorae.imagefactory.model.SDModelOption
import com.yessorae.imagefactory.model.SchedulerOption
import com.yessorae.imagefactory.model.type.toOptionList
import com.yessorae.imagefactory.model.type.toSDSizeType
import com.yessorae.imagefactory.model.type.toUpscaleType
import com.yessorae.imagefactory.ui.components.item.model.Option
import com.yessorae.imagefactory.ui.screen.main.tti.model.MoreEmbeddingsBottomSheet
import com.yessorae.imagefactory.ui.screen.main.tti.model.MoreLoRaModelBottomSheet
import com.yessorae.imagefactory.ui.screen.main.tti.model.MoreSDModelBottomSheet
import com.yessorae.imagefactory.ui.screen.main.tti.model.NegativePromptOptionAdditionDialog
import com.yessorae.imagefactory.ui.screen.main.tti.model.None
import com.yessorae.imagefactory.ui.screen.main.tti.model.PositivePromptAdditionDialog
import com.yessorae.imagefactory.ui.screen.main.tti.model.SeedChangeDialog
import com.yessorae.imagefactory.ui.screen.main.tti.model.TxtToImgDialogState
import com.yessorae.imagefactory.ui.screen.main.tti.model.TxtToImgResultDialog
import com.yessorae.imagefactory.ui.screen.main.tti.model.TxtToImgScreenState
import com.yessorae.imagefactory.util.HelpLink
import com.yessorae.imagefactory.util.ResString
import com.yessorae.imagefactory.util.StringModel
import com.yessorae.imagefactory.util.TextString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TxtToImgViewModel @Inject constructor(
    private val txtToImgRepository: TxtToImgRepository,
    private val publicModelMapper: PublicModelMapper,
    private val promptMapper: PromptMapper,
    private val txtToImgResultMapper: TxtToImgResultMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(TxtToImgScreenState())
    val uiState = _uiState.asStateFlow()

    private val _dialogEvent = MutableStateFlow<TxtToImgDialogState>(None)
    val dialogEvent = _dialogEvent.asStateFlow()

    private val _toast = MutableSharedFlow<StringModel>()
    val toast: SharedFlow<StringModel> = _toast.asSharedFlow()

    private val _saveImageEvent = MutableSharedFlow<String>()
    val saveImageEvent = _saveImageEvent.asSharedFlow()

    private val _redirectToWebBrowserEvent = MutableSharedFlow<String>()
    val redirectToWebBrowserEvent = _redirectToWebBrowserEvent.asSharedFlow()

    private val ceh = CoroutineExceptionHandler { _, throwable ->
        Logger.presentation(
            message = throwable.toString(), error = true
        )
    }

    private val scope = viewModelScope + ceh + Dispatchers.IO
    private val sharedEventScope = viewModelScope + Dispatchers.Main

    init {
        getPublicModels()
        getPositivePrompts()
        getNegativePrompts()
    }

    /** change value **/
    fun onSelectPositivePrompt(option: PromptOption) {
        val oldList = uiState.value.request.positivePromptOptions
        _uiState.update {
            uiState.value.copy(request = uiState.value.request.copy(positivePromptOptions = oldList.map { old ->
                if (old.id == option.id) {
                    option.copy(
                        selected = option.selected.not()
                    )
                } else {
                    old
                }
            }))
        }
    }

    fun onSelectNegativePrompt(option: PromptOption) {
        val oldList = uiState.value.request.negativePromptOptions
        _uiState.update {
            uiState.value.copy(request = uiState.value.request.copy(negativePromptOptions = oldList.map { old ->
                if (old.id == option.id) {
                    option.copy(
                        selected = option.selected.not()
                    )
                } else {
                    old
                }
            }))
        }
    }

    fun onChangeEnhancePrompt(enabled: Boolean) {
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    enhancePrompt = enabled
                )
            )
        }
    }

    fun onChangePromptStrength(guidanceScale: Int) {
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    guidanceScale = guidanceScale
                )
            )
        }
    }

    fun onSelectSDModel(option: SDModelOption) {
        val oldList = uiState.value.request.sdModelOption
        _uiState.update {
            uiState.value.copy(request = uiState.value.request.copy(sdModelOption = oldList.map { old ->
                if (old.id == option.id) {
                    old.copy(
                        selected = true
                    )
                } else {
                    old.copy(
                        selected = false
                    )
                }
            }))
        }
    }

    fun onSelectLoRaModel(option: LoRaModelOption) {
        val oldList = uiState.value.request.loRaModelsOptions
        _uiState.update {
            uiState.value.copy(request = uiState.value.request.copy(loRaModelsOptions = oldList.map { old ->
                if (old.id == option.id) {
                    old.copy(
                        selected = option.selected.not()
                    )
                } else {
                    old
                }
            }))
        }
    }

    fun onChangeLoRaModelStrength(option: LoRaModelOption, strength: Float) {
        val oldList = uiState.value.request.loRaModelsOptions
        _uiState.update {
            uiState.value.copy(request = uiState.value.request.copy(loRaModelsOptions = oldList.map { old ->
                if (old.id == option.id) {
                    old.copy(
                        strength = strength
                    )
                } else {
                    old
                }
            }))
        }

    }

    fun onSelectEmbeddingsModel(option: EmbeddingsModelOption) {
        val oldList = uiState.value.request.embeddingsModelOption
        _uiState.update {
            uiState.value.copy(request = uiState.value.request.copy(embeddingsModelOption = oldList.map { old ->
                if (old.id == option.id) {
                    old.copy(
                        selected = option.selected.not()
                    )
                } else {
                    old
                }
            }))
        }
    }

    fun onSelectSizeType(option: Option) {
        val sizeType = option.toSDSizeType()
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    sizeOption = sizeType.toOptionList()
                )
            )
        }
    }

    fun onChangeStepCount(stepCount: Int) {
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    stepCount = stepCount
                )
            )
        }
    }

    fun onChangeSeed(seed: Long?) {
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    seed = seed
                )
            )
        }

        cancelDialog()
    }

    fun onChangeScheduler(scheduler: SchedulerOption) {
        val oldList = uiState.value.request.schedulerOption
        _uiState.update {
            uiState.value.copy(request = uiState.value.request.copy(schedulerOption = oldList.map { old ->
                if (old.id == scheduler.id) {
                    old.copy(
                        selected = true
                    )
                } else {
                    old.copy(
                        selected = false
                    )
                }
            }))
        }
    }

    fun onChangeUpscale(upscale: Option) {
        val upscaleType = upscale.toUpscaleType()
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    upscaleOption = upscaleType.toOptionList()
                )
            )
        }

    }

    fun onAddPositivePrompt(prompt: String) {
        addPositivePrompt(prompt = prompt)
        cancelDialog()
    }

    fun onAddNegativePrompt(prompt: String) {
        addNegativePrompt(prompt = prompt)
        cancelDialog()
    }

    private fun addPositivePrompt(prompt: String) = scope.launch {
        txtToImgRepository.insertPrompt(promptMapper.mapToEntity(prompt = prompt, positive = true))
        val oldSet = uiState.value.request.positivePromptOptions.associateBy(keySelector = {
            it.id
        }, valueTransform = { it })

        val newList =
            promptMapper.map(txtToImgRepository.getPositivePrompts()).mapIndexed { _, new ->
                val old = oldSet[new.id]
                if (old != null) {
                    new.copy(
                        selected = old.selected
                    )
                } else {
                    new
                }
            }

        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    positivePromptOptions = newList
                )
            )
        }
    }

    private fun addNegativePrompt(prompt: String) = scope.launch {
        txtToImgRepository.insertPrompt(promptMapper.mapToEntity(prompt = prompt, positive = false))
        val oldSet = uiState.value.request.negativePromptOptions.associateBy(keySelector = {
            it.id
        }, valueTransform = { it })

        val newList =
            promptMapper.map(txtToImgRepository.getNegativePrompts()).mapIndexed { _, new ->
                val old = oldSet[new.id]
                if (old != null) {
                    new.copy(
                        selected = old.selected
                    )
                } else {
                    new
                }
            }

        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    negativePromptOptions = newList
                )
            )
        }
    }

    /** click event **/
    fun onClickAddPositivePrompt() = sharedEventScope.launch {
        _dialogEvent.emit(PositivePromptAdditionDialog)
    }

    fun onClickAddNegativePrompt() = sharedEventScope.launch {
        _dialogEvent.emit(NegativePromptOptionAdditionDialog)
    }

    fun onClickMorePositivePrompt() = scope.launch {

    }

    fun onClickMoreNegativePrompt() = scope.launch {

    }

    fun onClickMoreSDModel() = scope.launch {
        _dialogEvent.emit(
            MoreSDModelBottomSheet(
                options = uiState.value.request.sdModelOption,
            )
        )
    }

    fun onClickMoreLoRaModel() = scope.launch {
        _dialogEvent.emit(
            MoreLoRaModelBottomSheet(
                options = uiState.value.request.loRaModelsOptions,
            )
        )
    }

    fun onClickMoreEmbeddingsModel() = scope.launch {
        _dialogEvent.emit(
            MoreEmbeddingsBottomSheet(
                options = uiState.value.request.embeddingsModelOption,
            )
        )
    }

    fun onClickChangeSeed(currentSeed: Long?) = sharedEventScope.launch {
        _dialogEvent.emit(SeedChangeDialog(currentSeed = currentSeed))
    }

    fun onClickGenerateImage() {
        generateImage()
    }

    fun onClickRetry(data: TxtToImgResultDialog) {
        generateImage()
    }

    fun onClickSaveResultImage(data: TxtToImgResultDialog) = sharedEventScope.launch {
        data.result?.outputUrls?.forEach { url ->
            _saveImageEvent.emit(url)
        } ?: run {
            _toast.emit(ResString(R.string.common_state_still_load_image))
        }
    }

    fun onClickUpscale(resultBitmap: Bitmap?) = scope.launch {
        resultBitmap?.let { bitmap ->
            txtToImgRepository.upscaleImage(
                bitmap = bitmap,
                path = "stable-diffusion-tti-public",
                name = UUID.randomUUID().toString()
            )
        }
    }

    fun onClickHelp(languageCode: Locale) = viewModelScope.launch {
        GaEventManager.event(
            event = GaConstants.EVENT_CLICK_HELP,
            GaConstants.PARAM_LANGUAGE to languageCode.language
        )
        _redirectToWebBrowserEvent.emit(
            if (languageCode.language.contains("ko")) {
                HelpLink.KOREAN_HELP_LINK
            } else {
                HelpLink.GLOBAL_HELP_LINK
            }
        )
    }

    /** complete event **/
    fun onSaveComplete() = sharedEventScope.launch {
        _toast.emit(ResString(R.string.common_state_complete_save_image))
    }

    fun onSaveFailed(error: Throwable) = sharedEventScope.launch {
        _toast.emit(ResString(R.string.common_error_not_download_image_your_country))
        Logger.recordException(error = error)
    }

    fun onFailRedirectToWebBrowser() = sharedEventScope.launch {
        _toast.emit(ResString(R.string.common_state_error_redirect_web_browser))
    }

    /** dialog event **/
    fun onCancelDialog() {
        cancelDialog()
    }

    private fun cancelDialog() = sharedEventScope.launch {
        _dialogEvent.emit(None)
    }

    /** load **/
    private fun getPublicModels() = scope.launch {
        val models = txtToImgRepository.getPublicModels()
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    sdModelOption = publicModelMapper.mapSDModelOption(dto = models),
                    loRaModelsOptions = publicModelMapper.mapLoRaModelOption(dto = models),
                    embeddingsModelOption = publicModelMapper.mapEmbeddingsModelOption(dto = models)
                )
            )
        }
    }

    private fun getPositivePrompts() = scope.launch {
        val positive = txtToImgRepository.getPositivePrompts()
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    positivePromptOptions = promptMapper.map(dto = positive)
                )
            )
        }
    }

    private fun getNegativePrompts() = scope.launch {
        val negative = txtToImgRepository.getNegativePrompts()
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    negativePromptOptions = promptMapper.map(dto = negative)
                )
            )
        }
    }

    private fun showToast(message: StringModel) = sharedEventScope.launch {
        _toast.emit(message)
    }

    private fun showLoading(show: Boolean) {
        _uiState.update {
            uiState.value.copy(
                loading = show
            )
        }
    }

    private fun generateImage() = scope.launch {
        uiState.value.request.asTxtToImgRequest(
            toastEvent = { message ->
                showToast(message = message)
            }
        )?.let { request ->
            showLoading(show = true)
            val response = txtToImgRepository.generateImage(request = request)
            when (response.status) {
                "success" -> {
                    response.let { result ->
                        Logger.presentation("result ${result.output.map { it.replaceDomain() }}")
                        _dialogEvent.emit(
                            TxtToImgResultDialog(
                                request = uiState.value.request.copy(),
                                result = txtToImgResultMapper.map(
                                    dto = result.copy(
                                        output = result.output.map { it.replaceDomain() }
                                    )
                                ),
                                ratio = request.width / request.height.toFloat()
                            )
                        )
                    }
                }

                "processing" -> {
                    fetchQueuedImage(
                        request = request, id = response.id
                    )

                }

                else -> {
                    showToast(message = TextString(response.status))
                }
            }
            showLoading(show = false)
        }
    }

    private fun fetchQueuedImage(request: TxtToImgRequest, id: Int) {
        scope.launch {
            delay(3000L)
            val response = txtToImgRepository.fetchQueuedImage(requestId = id.toString())
            if (response.status == "processing") {
                fetchQueuedImage(request = request, id = response.id)
            } else if (response.status == "success") {
                _dialogEvent.emit(
                    TxtToImgResultDialog(
                        request = uiState.value.request.copy(), result = txtToImgResultMapper.map(
                            id = response.id,
                            outputUrls = response.output,
                            status = response.status,
                            generationTime = null
                        ), ratio = request.width / request.height.toFloat()
                    )
                )
            }
        }
    }
}
