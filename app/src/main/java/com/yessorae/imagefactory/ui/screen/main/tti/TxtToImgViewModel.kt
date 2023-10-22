package com.yessorae.imagefactory.ui.screen.main.tti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yessorae.common.Constants
import com.yessorae.common.GaConstants
import com.yessorae.common.GaEventManager
import com.yessorae.common.Logger
import com.yessorae.common.trueOrFalse
import com.yessorae.data.repository.PublicModelRepository
import com.yessorae.data.repository.TxtToImgHistoryRepository
import com.yessorae.data.repository.TxtToImgRepository
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.mapper.PromptMapper
import com.yessorae.imagefactory.mapper.PublicModelMapper
import com.yessorae.imagefactory.model.EmbeddingsModelOption
import com.yessorae.imagefactory.model.LoRaModelOption
import com.yessorae.imagefactory.model.PromptOption
import com.yessorae.imagefactory.model.SDModelOption
import com.yessorae.imagefactory.model.SchedulerOption
import com.yessorae.imagefactory.model.initialValues
import com.yessorae.imagefactory.model.type.SDSizeType
import com.yessorae.imagefactory.model.type.UpscaleType
import com.yessorae.imagefactory.model.type.toOptionList
import com.yessorae.imagefactory.model.type.toSDSizeType
import com.yessorae.imagefactory.model.type.toUpscaleType
import com.yessorae.imagefactory.ui.components.item.model.Option
import com.yessorae.imagefactory.ui.navigation.destination.TxtToImgResultDestination
import com.yessorae.imagefactory.ui.screen.main.tti.model.TxtToImgDialog
import com.yessorae.imagefactory.ui.screen.main.tti.model.TxtToImgRequestOption.Companion.DEFAULT_GUIDANCE_SCALE
import com.yessorae.imagefactory.ui.screen.main.tti.model.TxtToImgRequestOption.Companion.DEFAULT_SAMPLES
import com.yessorae.imagefactory.ui.screen.main.tti.model.TxtToImgRequestOption.Companion.DEFAULT_STEP_COUNT
import com.yessorae.imagefactory.ui.screen.main.tti.model.TxtToImgScreenState
import com.yessorae.imagefactory.util.HelpLink
import com.yessorae.imagefactory.util.ResString
import com.yessorae.imagefactory.util.StringModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TxtToImgViewModel @Inject constructor(
    private val txtToImgRepository: TxtToImgRepository,
    private val publicModelRepository: PublicModelRepository,
    private val txtToImgHistoryRepository: TxtToImgHistoryRepository,
    private val publicModelMapper: PublicModelMapper,
    private val promptMapper: PromptMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(TxtToImgScreenState())
    val uiState = _uiState.asStateFlow()

    private val _toast = MutableSharedFlow<StringModel>()
    val toast: SharedFlow<StringModel> = _toast.asSharedFlow()

    private val _redirectToWebBrowserEvent = MutableSharedFlow<String>()
    val redirectToWebBrowserEvent = _redirectToWebBrowserEvent.asSharedFlow()

    private val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent: SharedFlow<String> = _navigationEvent.asSharedFlow()

    private val ceh = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _toast.emit(ResString(R.string.common_response_error))
        }
        Logger.presentation(
            message = throwable.toString(),
            error = true
        )
    }

    private val scope = viewModelScope + ceh + Dispatchers.IO
    private val sharedEventScope = viewModelScope + Dispatchers.Main

    init {
        initTxtToImgRequestOption()
    }

    /** change value **/
    fun onSelectPositivePrompt(option: PromptOption) {
        val oldList = uiState.value.request.positivePromptOptions
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    positivePromptOptions = oldList.map { old ->
                        if (old.id == option.id) {
                            option.copy(
                                selected = option.selected.not()
                            )
                        } else {
                            old
                        }
                    }
                )
            )
        }
    }

    fun onSelectNegativePrompt(option: PromptOption) {
        val oldList = uiState.value.request.negativePromptOptions
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    negativePromptOptions = oldList.map { old ->
                        if (old.id == option.id) {
                            option.copy(
                                selected = option.selected.not()
                            )
                        } else {
                            old
                        }
                    }
                )
            )
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
            uiState.value.copy(
                request = uiState.value.request.copy(
                    sdModelOption = oldList.map { old ->
                        if (old.id == option.id) {
                            old.copy(
                                selected = true
                            )
                        } else {
                            old.copy(
                                selected = false
                            )
                        }
                    }
                )
            )
        }
    }

    fun onSelectLoRaModel(option: LoRaModelOption) {
        val oldList = uiState.value.request.loRaModelsOptions
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    loRaModelsOptions = oldList.map { old ->
                        if (old.id == option.id) {
                            old.copy(
                                selected = option.selected.not()
                            )
                        } else {
                            old
                        }
                    }
                )
            )
        }
    }

    fun onChangeLoRaModelStrength(option: LoRaModelOption, strength: Float) {
        val oldList = uiState.value.request.loRaModelsOptions
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    loRaModelsOptions = oldList.map { old ->
                        if (old.id == option.id) {
                            old.copy(
                                strength = strength
                            )
                        } else {
                            old
                        }
                    }
                )
            )
        }
    }

    fun onSelectEmbeddingsModel(option: EmbeddingsModelOption) {
        val oldList = uiState.value.request.embeddingsModelOption
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    embeddingsModelOption = oldList.map { old ->
                        if (old.id == option.id) {
                            old.copy(
                                selected = option.selected.not()
                            )
                        } else {
                            old
                        }
                    }
                )
            )
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
            uiState.value.copy(
                request = uiState.value.request.copy(
                    schedulerOption = oldList.map { old ->
                        if (old.id == scheduler.id) {
                            old.copy(
                                selected = true
                            )
                        } else {
                            old.copy(
                                selected = false
                            )
                        }
                    }
                )
            )
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
        val newPromptId = txtToImgRepository.insertPrompt(
            prompt = promptMapper.mapToEntity(
                prompt = prompt,
                positive = true
            )
        )
        val oldSet = uiState.value.request.positivePromptOptions.associateBy(
            keySelector = { it.id },
            valueTransform = { it }
        )

        val newList =
            promptMapper.map(txtToImgRepository.getPositivePrompts()).mapIndexed { _, new ->
                val old = oldSet[new.id]
                new.copy(
                    selected = old?.selected == true || new.id == newPromptId
                )
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
        val newPromptId = txtToImgRepository.insertPrompt(
            promptMapper.mapToEntity(
                prompt = prompt,
                positive = false
            )
        )
        val oldSet = uiState.value.request.negativePromptOptions.associateBy(
            keySelector = { it.id },
            valueTransform = { it }
        )

        val newList =
            promptMapper.map(txtToImgRepository.getNegativePrompts()).mapIndexed { _, new ->
                val old = oldSet[new.id]
                new.copy(
                    selected = old?.selected == true || new.id == newPromptId
                )
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
        _uiState.update {
            uiState.value.copy(
                dialogState = TxtToImgDialog.PositivePromptAddition
            )
        }
    }

    fun onClickAddNegativePrompt() = sharedEventScope.launch {
        _uiState.update {
            uiState.value.copy(
                dialogState = TxtToImgDialog.NegativePromptOptionAddition
            )
        }
    }

    fun onClickMoreSDModel() = scope.launch {
        _uiState.update {
            uiState.value.copy(
                dialogState = TxtToImgDialog.MoreSDModelBottomSheet(
                    options = uiState.value.request.sdModelOption
                )
            )
        }
    }

    fun onClickMoreLoRaModel() = scope.launch {
        _uiState.update {
            uiState.value.copy(
                dialogState = TxtToImgDialog.MoreLoRaModelBottomSheet(
                    options = uiState.value.request.loRaModelsOptions
                )
            )
        }
    }

    fun onClickMoreEmbeddingsModel() = scope.launch {
        _uiState.update {
            uiState.value.copy(
                dialogState = TxtToImgDialog.MoreEmbeddingsBottomSheet(
                    options = uiState.value.request.embeddingsModelOption
                )
            )
        }
    }

    fun onClickChangeSeed(currentSeed: Long?) = sharedEventScope.launch {
        _uiState.update {
            uiState.value.copy(
                dialogState = TxtToImgDialog.SeedChange(currentSeed = currentSeed)
            )
        }
    }

    fun onClickGenerateImage() {
        generateImage()
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

    fun onFailRedirectToWebBrowser() = sharedEventScope.launch {
        _toast.emit(ResString(R.string.common_state_error_redirect_web_browser))
    }

    /** dialog event **/
    fun onCancelDialog() {
        cancelDialog()
    }

    private fun cancelDialog() = sharedEventScope.launch {
        _uiState.update {
            uiState.value.copy(
                dialogState = TxtToImgDialog.None
            )
        }
    }

    /** load  **/
    private fun initTxtToImgRequestOption() = scope.launch {
        val lastRequest =
            txtToImgHistoryRepository.getLastTxtToImgHistory()?.request

        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    enhancePrompt = lastRequest?.enhancePrompt?.trueOrFalse() ?: false,
                    stepCount = lastRequest?.numInferenceSteps ?: DEFAULT_STEP_COUNT,
                    safetyChecker = lastRequest?.safetyChecker?.trueOrFalse() ?: false,
                    sizeOption = SDSizeType.createOptions(
                        width = lastRequest?.width,
                        height = lastRequest?.height
                    ),
                    seed = lastRequest?.seed?.toLongOrNull(),
                    guidanceScale = lastRequest?.guidanceScale?.toInt() ?: DEFAULT_GUIDANCE_SCALE,
                    upscaleOption = UpscaleType.createOptions(lastUpscaleNumber = lastRequest?.upscale?.toIntOrNull()),
                    samples = lastRequest?.samples ?: DEFAULT_SAMPLES,
                    schedulerOption = SchedulerOption.initialValues(lastSchedulerId = lastRequest?.scheduler)
                )
            )
        }

        getPublicModels(
            modelId = lastRequest?.modelId?.trim(),
            loRaModels = lastRequest?.loraModel
                ?.split(Constants.SEPARATOR_DEFAULTS)
                ?.map {
                    it.trim()
                },
            loRaStrength = lastRequest?.loraStrength
                ?.split(Constants.SEPARATOR_DEFAULTS)
                ?.map {
                    it.toFloatOrNull()
                }
                ?.filterNotNull(),
            embeddingsIds = lastRequest?.embeddingsModel
                ?.split(Constants.SEPARATOR_DEFAULTS)
                ?.map {
                    it.trim()
                }
        )
        getPositivePrompts(
            lastPrompts = lastRequest?.prompt
                ?.split(Constants.SEPARATOR_DEFAULTS)
                ?.map {
                    it.trim()
                }
        )
        getNegativePrompts(
            lastPrompts = lastRequest?.negativePrompt
                ?.split(Constants.SEPARATOR_DEFAULTS)
                ?.map { it.trim() }
        )
    }

    private fun getPublicModels(
        modelId: String?,
        loRaModels: List<String>?,
        loRaStrength: List<Float>?,
        embeddingsIds: List<String>?
    ) = scope.launch {
        val models = publicModelRepository.getPublicModels()
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    sdModelOption = publicModelMapper.mapSDModelOption(
                        dto = models,
                        lastModelId = modelId
                    ),
                    loRaModelsOptions = publicModelMapper.mapLoRaModelOption(
                        dto = models,
                        lastIds = loRaModels,
                        lastStrength = loRaStrength
                    ),
                    embeddingsModelOption = publicModelMapper.mapEmbeddingsModelOption(
                        dto = models,
                        lastIds = embeddingsIds
                    )
                ),
                modelLoading = false
            )
        }
    }

    private fun getPositivePrompts(
        lastPrompts: List<String>?
    ) = scope.launch {
        val positive = txtToImgRepository.getPositivePrompts()
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    positivePromptOptions = promptMapper.map(
                        dto = positive,
                        lastPromptIds = lastPrompts
                    )
                )
            )
        }
    }

    private fun getNegativePrompts(
        lastPrompts: List<String>?
    ) = scope.launch {
        val negative = txtToImgRepository.getNegativePrompts()
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    negativePromptOptions = promptMapper.map(
                        dto = negative,
                        lastPromptIds = lastPrompts
                    )
                )
            )
        }
    }

    private fun showToast(message: StringModel) = sharedEventScope.launch {
        _toast.emit(message)
    }

    private fun generateImage() = scope.launch {
        uiState.value.request.asTxtToImgRequest(
            toastEvent = { message ->
                showToast(message = message)
            }
        )?.let { request ->
            val requestId = txtToImgHistoryRepository.insertRequestHistory(request)
            _navigationEvent.emit(
                TxtToImgResultDestination.getRouteWithArgs(requestId = requestId.toInt())
            )
        }
    }
}
