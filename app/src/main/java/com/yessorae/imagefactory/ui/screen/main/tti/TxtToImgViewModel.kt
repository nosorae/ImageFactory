package com.yessorae.imagefactory.ui.screen.main.tti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yessorae.common.GaConstants
import com.yessorae.common.GaEventManager
import com.yessorae.common.Logger
import com.yessorae.data.repository.PublicModelRepository
import com.yessorae.data.repository.TxtToImgHistoryRepository
import com.yessorae.data.repository.TxtToImgRepository
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.mapper.PromptMapper
import com.yessorae.imagefactory.mapper.PublicModelMapper
import com.yessorae.imagefactory.mapper.TxtToImgResultMapper
import com.yessorae.imagefactory.mapper.UpscaleResultModelMapper
import com.yessorae.imagefactory.model.EmbeddingsModelOption
import com.yessorae.imagefactory.model.LoRaModelOption
import com.yessorae.imagefactory.model.PromptOption
import com.yessorae.imagefactory.model.SDModelOption
import com.yessorae.imagefactory.model.SchedulerOption
import com.yessorae.imagefactory.model.type.toOptionList
import com.yessorae.imagefactory.model.type.toSDSizeType
import com.yessorae.imagefactory.model.type.toUpscaleType
import com.yessorae.imagefactory.ui.components.item.model.Option
import com.yessorae.imagefactory.ui.navigation.destination.TxtToImgResultDestination
import com.yessorae.imagefactory.ui.screen.main.tti.model.MoreEmbeddingsBottomSheet
import com.yessorae.imagefactory.ui.screen.main.tti.model.MoreLoRaModelBottomSheet
import com.yessorae.imagefactory.ui.screen.main.tti.model.MoreSDModelBottomSheet
import com.yessorae.imagefactory.ui.screen.main.tti.model.NegativePromptOptionAdditionDialog
import com.yessorae.imagefactory.ui.screen.main.tti.model.None
import com.yessorae.imagefactory.ui.screen.main.tti.model.PositivePromptAdditionDialog
import com.yessorae.imagefactory.ui.screen.main.tti.model.SeedChangeDialog
import com.yessorae.imagefactory.ui.screen.main.tti.model.TxtToImgDialogState
import com.yessorae.imagefactory.ui.screen.main.tti.model.TxtToImgOptionState
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
    private val promptMapper: PromptMapper,
    private val txtToImgResultMapper: TxtToImgResultMapper,
    private val upscaleResultModelMapper: UpscaleResultModelMapper
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
        getPublicModels()
        getPositivePrompts()
        getNegativePrompts()
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
                options = uiState.value.request.sdModelOption
            )
        )
    }

    fun onClickMoreLoRaModel() = scope.launch {
        _dialogEvent.emit(
            MoreLoRaModelBottomSheet(
                options = uiState.value.request.loRaModelsOptions
            )
        )
    }

    fun onClickMoreEmbeddingsModel() = scope.launch {
        _dialogEvent.emit(
            MoreEmbeddingsBottomSheet(
                options = uiState.value.request.embeddingsModelOption
            )
        )
    }

    fun onClickChangeSeed(currentSeed: Long?) = sharedEventScope.launch {
        _dialogEvent.emit(SeedChangeDialog(currentSeed = currentSeed))
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
        _dialogEvent.emit(None)
    }

    /** load **/
    private fun getPublicModels() = scope.launch {
        val models = publicModelRepository.getPublicModels()
        _uiState.update {
            uiState.value.copy(
                request = uiState.value.request.copy(
                    sdModelOption = publicModelMapper.mapSDModelOption(dto = models),
                    loRaModelsOptions = publicModelMapper.mapLoRaModelOption(dto = models),
                    embeddingsModelOption = publicModelMapper.mapEmbeddingsModelOption(dto = models)
                ),
                modelLoading = false
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

    private fun generateImage(requestOptionModel: TxtToImgOptionState? = null) = scope.launch {
        (requestOptionModel ?: uiState.value.request).asTxtToImgRequest(
            toastEvent = { message ->
                showToast(message = message)
            }
        )?.let { request ->
            val requestId = txtToImgHistoryRepository.insertRequestHistory(request)
            txtToImgRepository.setLastRequest(request = request)
            _navigationEvent.emit(
                TxtToImgResultDestination.getRouteWithArgs(requestId = requestId.toInt())
            )
        }
    }
}
