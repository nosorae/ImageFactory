package com.yessorae.presentation.ui.screen.main.tti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yessorae.common.Logger
import com.yessorae.domain.model.TxtToImgHistory
import com.yessorae.domain.model.TxtToImgRequest
import com.yessorae.domain.model.parameter.Prompt
import com.yessorae.domain.usecase.DeletePromptUseCase
import com.yessorae.domain.usecase.GetAllEmbeddingsModelsUseCase
import com.yessorae.domain.usecase.GetAllLoRaModelsUseCase
import com.yessorae.domain.usecase.GetAllSDModelsUseCase
import com.yessorae.domain.usecase.GetFeaturedEmbeddingsModelsUseCase
import com.yessorae.domain.usecase.GetFeaturedLoRaModelsUseCase
import com.yessorae.domain.usecase.GetFeaturedSDModelsUseCase
import com.yessorae.domain.usecase.GetNegativePromptsFlowUseCase
import com.yessorae.domain.usecase.GetPositivePromptsFlowUseCase
import com.yessorae.domain.usecase.InsertPromptUseCase
import com.yessorae.domain.usecase.InsertTxtToImgHistoryUseCase
import com.yessorae.domain.usecase.InsertUsedEmbeddingsModelUseCase
import com.yessorae.domain.usecase.InsertUsedLoRaModelUseCase
import com.yessorae.domain.usecase.InsertUsedSDModelUseCase
import com.yessorae.domain.util.GaConstants
import com.yessorae.domain.util.StableDiffusionConstants
import com.yessorae.domain.util.StableDiffusionConstants.ARG_NO
import com.yessorae.domain.util.StableDiffusionConstants.ARG_YES
import com.yessorae.domain.util.StableDiffusionConstants.INITIAL_GUIDANCE_SCALE
import com.yessorae.domain.util.StableDiffusionConstants.INITIAL_STEP_COUNT
import com.yessorae.domain.util.isMultiLanguage
import com.yessorae.presentation.R
import com.yessorae.presentation.navigation.destination.TxtToImgResultDestination
import com.yessorae.presentation.ui.screen.main.tti.model.LoRaModelOption
import com.yessorae.presentation.ui.screen.main.tti.model.PromptOption
import com.yessorae.presentation.ui.screen.main.tti.model.EmbeddingsModelOption
import com.yessorae.presentation.ui.screen.main.tti.model.SDModelOption
import com.yessorae.presentation.ui.screen.main.tti.model.SchedulerOption
import com.yessorae.presentation.util.helper.StringResourceHelper
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
import com.yessorae.presentation.ui.screen.main.tti.model.TxtToImgDialog
import com.yessorae.presentation.ui.screen.main.tti.model.asDomainModel
import com.yessorae.presentation.ui.screen.main.tti.model.asOption
import com.yessorae.presentation.util.GaEventManager
import com.yessorae.presentation.util.HelpLink
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.plus
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TxtToImgViewModel @Inject constructor(
    private val getPositivePromptsFlowUseCase: GetPositivePromptsFlowUseCase,
    private val getNegativePromptsFlowUseCase: GetNegativePromptsFlowUseCase,
    private val getFeaturedSDModelsUseCase: GetFeaturedSDModelsUseCase,
    private val getFeaturedLoRaModelsUseCase: GetFeaturedLoRaModelsUseCase,
    private val getFeaturedEmbeddingsModelsUseCase: GetFeaturedEmbeddingsModelsUseCase,
    private val insertPromptUseCase: InsertPromptUseCase,
    private val deletePromptUseCase: DeletePromptUseCase,
    private val getAllSDModelsUseCase: GetAllSDModelsUseCase,
    private val getAllLoRaModelsUseCase: GetAllLoRaModelsUseCase,
    private val getAllEmbeddingsModelsUseCase: GetAllEmbeddingsModelsUseCase,
    private val insertUsedSDModelUseCase: InsertUsedSDModelUseCase,
    private val insertUsedLoRaModelUseCase: InsertUsedLoRaModelUseCase,
    private val insertUsedEmbeddingsModelUseCase: InsertUsedEmbeddingsModelUseCase,
    private val insertTxtToImgHistoryUseCase: InsertTxtToImgHistoryUseCase,
    private val stringResourceHelper: StringResourceHelper
) : ViewModel() {
    /** state field */
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

    private val _featuredSDModelOptions = MutableStateFlow<List<SDModelOption>>(listOf())
    val featuredSdModelOptions = _featuredSDModelOptions.asStateFlow().onSubscription {
        subscribeFeaturedSDModels()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    private val _featuredLoRaModelOptions = MutableStateFlow<List<LoRaModelOption>>(listOf())
    val featuredLoRaModelOptions = _featuredLoRaModelOptions.asStateFlow().onSubscription {
        subscribeFeaturedLoRaModels()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf() // TODO:: SR-N check
    )

    private val _featuredSelectedLoRaModelOptions =
        MutableStateFlow<List<LoRaModelOption>>(listOf())
    val selectedFeaturdLoRaModelOptions = _featuredSelectedLoRaModelOptions.asStateFlow()

    private val _featuredEmbeddingsModelOptions =
        MutableStateFlow<List<EmbeddingsModelOption>>(listOf())
    val featuredEmbeddingsModelOptions =
        _featuredEmbeddingsModelOptions.asStateFlow().onSubscription {
            subscribeFeaturedEmbeddingsModels()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = listOf() // TODO:: SR-N check
        )

    private val _schedulerOptions = MutableStateFlow(SchedulerOption.initialScheduler)
    val schedulerOptions = _schedulerOptions.asStateFlow()

    private val _stepCount = MutableStateFlow(INITIAL_STEP_COUNT)
    val stepCount = _stepCount.asStateFlow()

    private val _guidanceScale = MutableStateFlow(INITIAL_GUIDANCE_SCALE)
    val guidanceScale = _guidanceScale.asStateFlow()

    private val _dialog = MutableStateFlow<TxtToImgDialog>(TxtToImgDialog.None)
    val dialog = _dialog.asStateFlow()

    private val _allSDModelOptions = MutableStateFlow<List<SDModelOption>>(listOf())
    val allSDModelOptions = _allSDModelOptions.asStateFlow().onSubscription {
        getAllSDModels()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf() // TODO:: SR-N check
    )

    private val _allLoRaModelOptions = MutableStateFlow<List<LoRaModelOption>>(listOf())
    val allLoRaModelOptions = _allLoRaModelOptions.asStateFlow().onSubscription {
        getAllLoRaModels()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf() // TODO:: SR-N check
    )

    private val _allEmbeddingsModelOptions = MutableStateFlow<List<EmbeddingsModelOption>>(listOf())
    val allEmbeddingsModelOptions = _allEmbeddingsModelOptions.asStateFlow().onSubscription {
        getAllEmbeddingsModels()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf() // TODO:: SR-N check
    )

    private val _loading = MutableStateFlow<Boolean>(false)
    val loading = _loading.asStateFlow()

    /** event state field */
    private val _toast = MutableSharedFlow<String>()
    val toast: SharedFlow<String> = _toast.asSharedFlow()

    private val _redirectToWebBrowserEvent = MutableSharedFlow<String>()
    val redirectToWebBrowserEvent = _redirectToWebBrowserEvent.asSharedFlow()

    private val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent: SharedFlow<String> = _navigationEvent.asSharedFlow()


    /** etc field */
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

    private val selectedLoRaModels: List<LoRaModelOption>
        get() {
            return selectedFeaturdLoRaModelOptions.value
        }

    private val selectedEmbeddingsModel: EmbeddingsModelOption?
        get() {
            return featuredEmbeddingsModelOptions.value.firstOrNull { it.selected }
        }

    private val selectedScheduler: SchedulerOption?
        get() {
            return schedulerOptions.value.firstOrNull { it.selected }
        }

    private val ceh = CoroutineExceptionHandler { _, throwable ->

        viewModelScope.launch {
            _toast.emit(stringResourceHelper.getString(R.string.common_response_unknown_error))
        }
        Logger.presentation(
            message = throwable.toString(),
            error = true
        )
    }

    private val scope = viewModelScope + ceh + Dispatchers.IO
    private val sharedEventScope = viewModelScope + Dispatchers.Main

    /** user interaction **/
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

    fun longClickPrompt(clickedOption: PromptOption) = scope.launch {
        _dialog.update {
            TxtToImgDialog.PromptDeletionConfirmation(
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

    fun clickLoRaModel(clickedOption: LoRaModelOption) {
        _featuredLoRaModelOptions.update { list ->
            list.map { option ->
                if (clickedOption.model.id == option.model.id) {
                    option.copy(
                        selected = option.selected.not()
                    )
                } else {
                    option
                }
            }
        }

        _featuredSelectedLoRaModelOptions.update { _ ->
            featuredLoRaModelOptions.value.filter { it.selected }
        }
    }

    fun changeLoRaModelStrength(clickedOption: LoRaModelOption, strength: Float) {
        _featuredSelectedLoRaModelOptions.update { list ->
            list.map { option ->
                if (clickedOption.model.id == option.model.id) {
                    option.copy(
                        model = option.model.copy(
                            strength = strength
                        )
                    )
                } else {
                    option
                }
            }
        }
    }

    fun clickEmbeddingsModel(clickedOption: EmbeddingsModelOption) {
        _featuredEmbeddingsModelOptions.update { list ->
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

    fun changeStepCount(stepCount: Int) {
        _stepCount.update { stepCount }
    }

    fun changeScheduler(clickedOption: SchedulerOption) {
        _schedulerOptions.update { list ->
            list.map { option ->
                option.copy(selected = clickedOption.scheduler.id == option.scheduler.id)
            }
        }
    }

    fun clickAddPositivePrompt(prompt: String) = scope.launch {
        insertPromptUseCase(
            prompt = Prompt.create(
                prompt = prompt,
                positive = true
            )
        )
        cancelDialog()
    }

    fun clickAddNegativePrompt(prompt: String) = scope.launch {
        insertPromptUseCase(
            prompt = Prompt.create(
                prompt = prompt,
                positive = false
            )
        )
        cancelDialog()
    }

    fun clickDeletePrompt(prompt: PromptOption) = scope.launch {
        deletePromptUseCase(prompt = prompt.asDomainModel())
    }

    fun clickToAddPositivePrompt() = sharedEventScope.launch {
        _dialog.update {
            TxtToImgDialog.PositivePromptAddition
        }
    }

    fun clickToAddNegativePrompt() = sharedEventScope.launch {
        _dialog.update {
            TxtToImgDialog.NegativePromptOptionAddition
        }
    }

    fun clickMoreSDModel() = scope.launch {
        _dialog.update {
            TxtToImgDialog.MoreSDModelBottomSheet
        }
    }

    fun clickMoreSDModel(model: SDModelOption) = scope.launch {
        insertUsedSDModelUseCase(model.model)
        showToast(message = stringResourceHelper.getString(R.string.common_model_added))
    }

    fun clickMoreLoRaModel() = scope.launch {
        _dialog.update {
            TxtToImgDialog.MoreLoRaModelBottomSheet
        }
    }

    fun clickMoreLoRaModel(model: LoRaModelOption) = scope.launch {
        insertUsedLoRaModelUseCase(model.model)
        showToast(message = stringResourceHelper.getString(R.string.common_model_added))
    }

    fun clickMoreEmbeddingsModel() = scope.launch {
        _dialog.update {
            TxtToImgDialog.MoreEmbeddingsBottomSheet
        }
    }

    fun clickMoreEmbeddingsModel(model: EmbeddingsModelOption) = scope.launch {
        insertUsedEmbeddingsModelUseCase(model.model)
        showToast(message = stringResourceHelper.getString(R.string.common_model_added))
    }

    fun clickGenerateImage() {
        generateImage()
    }

    fun clickHelp(languageCode: Locale) = viewModelScope.launch {
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

    fun clickCancelDialog() {
        cancelDialog()
    }

    /** complete event */

    fun onFailRedirectToWebBrowser() = sharedEventScope.launch {
        showToast(stringResourceHelper.getString(R.string.common_state_error_redirect_web_browser))
    }

    /** request, load ..  */
    private fun subscribePositivePrompts() = scope.launch {
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

    private fun subscribeNegativePrompts() = scope.launch {
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

    private fun subscribeFeaturedSDModels() = scope.launch {
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

    private fun subscribeFeaturedLoRaModels() = scope.launch {
        getFeaturedLoRaModelsUseCase().collectLatest { models ->
            val selectedModelSet = selectedLoRaModels.map { it.model.id }.toSet()
            _featuredLoRaModelOptions.update {
                models.map { model ->
                    val modelOption = model.asOption()
                    modelOption.copy(selected = selectedModelSet.contains(modelOption.model.id))
                }
            }
        }
    }

    private fun subscribeFeaturedEmbeddingsModels() = scope.launch {
        getFeaturedEmbeddingsModelsUseCase().collectLatest { models ->
            val selectedModel = selectedEmbeddingsModel
            _featuredEmbeddingsModelOptions.update {
                models.map { model ->
                    val modelOption = model.asOption()
                    modelOption.copy(selected = selectedModel?.model?.id == modelOption.model.id)
                }
            }
        }
    }

    private fun getAllSDModels() = scope.launch {
        val selectedModel = selectedSDModel
        _allSDModelOptions.update {
            getAllSDModelsUseCase().map { model ->
                val modelOption = model.asOption()
                modelOption.copy(selected = selectedModel?.model?.id == modelOption.model.id)
            }
        }
    }

    private fun getAllLoRaModels() = scope.launch {
        val selectedModelSet = selectedLoRaModels.map { it.model.id }.toSet()
        _allLoRaModelOptions.update {
            getAllLoRaModelsUseCase().map { model ->
                val modelOption = model.asOption()
                modelOption.copy(selected = selectedModelSet.contains(modelOption.model.id))
            }
        }
    }

    private fun getAllEmbeddingsModels() = scope.launch {
        val selectedModel = selectedEmbeddingsModel
        _allEmbeddingsModelOptions.update {
            getAllEmbeddingsModelsUseCase().map { model ->
                val modelOption = model.asOption()
                modelOption.copy(selected = selectedModel?.model?.id == modelOption.model.id)
            }
        }
    }


    fun generateImage() = scope.launch {
        // 조건 충족 안되었을 때는 에러 그에 맞는 에러 토스트 띄우기
        // 로컬디비에 요청 저장 -> id 받아오고
        // id 전달하면서 결과화면으로 이동
        val positivePrompts = selectedPositivePrompts
            .joinToString(StableDiffusionConstants.PROMPT_SEPARATOR) { it.prompt }

        val negativePrompts = selectedNegativePrompts
            .joinToString(StableDiffusionConstants.PROMPT_SEPARATOR) { it.prompt }

        val sdModelId = selectedSDModel?.model?.id

        val loRaModels = selectedLoRaModels
            .joinToString(StableDiffusionConstants.PROMPT_SEPARATOR) { it.model.id }

        val loRaModelStrengths = selectedLoRaModels
            .joinToString(StableDiffusionConstants.PROMPT_SEPARATOR) { it.model.strength.toString() }

        val embeddingsModelsId = selectedEmbeddingsModel?.model?.id

        val schedulerId = selectedScheduler?.scheduler?.id

        val multiLingual = if (positivePrompts.isMultiLanguage() ||
            negativePrompts.isMultiLanguage()
        ) {
            ARG_YES
        } else {
            ARG_NO
        }

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

        val history = TxtToImgHistory(
            request = TxtToImgRequest(
                prompt = positivePrompts,
                modelId = sdModelId,
                lora = loRaModels,
                loraStrength = loRaModelStrengths,
                embeddings = embeddingsModelsId,
                negativePrompt = negativePrompts,
                guidanceScale = guidanceScale.value.toDouble(),
                steps = stepCount.value,
                multiLingual = multiLingual,
                scheduler = schedulerId
            )
        )

        val historyId = insertTxtToImgHistoryUseCase(request = history)

        _navigationEvent.emit(
            TxtToImgResultDestination.getRouteWithArgs(requestId = historyId.toInt())
        )
    }

    /** etc **/
    private fun cancelDialog() = sharedEventScope.launch {
        _dialog.update {
            TxtToImgDialog.None
        }
    }

    private fun showToast(message: String) = sharedEventScope.launch {
        _toast.emit(message)
    }

}
