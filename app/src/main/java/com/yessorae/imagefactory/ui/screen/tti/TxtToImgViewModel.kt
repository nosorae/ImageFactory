package com.yessorae.imagefactory.ui.screen.tti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yessorae.common.Constants
import com.yessorae.common.Logger
import com.yessorae.common.replaceDomain
import com.yessorae.data.remote.model.request.TxtToImgRequest
import com.yessorae.data.repository.TxtToImgRepository
import com.yessorae.imagefactory.mapper.PromptMapper
import com.yessorae.imagefactory.mapper.PublicModelMapper
import com.yessorae.imagefactory.model.EmbeddingsModelOption
import com.yessorae.imagefactory.model.LoRaModelOption
import com.yessorae.imagefactory.model.PromptOption
import com.yessorae.imagefactory.model.SDModelOption
import com.yessorae.imagefactory.model.SchedulerOption
import com.yessorae.imagefactory.model.type.toOptionList
import com.yessorae.imagefactory.model.type.toSDSizeType
import com.yessorae.imagefactory.model.type.toUpscaleType
import com.yessorae.imagefactory.ui.components.item.model.Option
import com.yessorae.imagefactory.ui.screen.tti.model.NegativePromptOptionAdditionDialog
import com.yessorae.imagefactory.ui.screen.tti.model.None
import com.yessorae.imagefactory.ui.screen.tti.model.PositivePromptAdditionDialog
import com.yessorae.imagefactory.ui.screen.tti.model.SeedChangeDialog
import com.yessorae.imagefactory.ui.screen.tti.model.TxtToImgDialogState
import com.yessorae.imagefactory.ui.screen.tti.model.TxtToImgScreenState
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString
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
import javax.inject.Inject

@HiltViewModel
class TxtToImgViewModel @Inject constructor(
    private val txtToImgRepository: TxtToImgRepository,
    private val publicModelMapper: PublicModelMapper,
    private val promptMapper: PromptMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(TxtToImgScreenState())
    val uiState = _uiState.asStateFlow()

    private val _dialogEvent = MutableStateFlow<TxtToImgDialogState>(None)
    val dialogEvent = _dialogEvent.asStateFlow()

    protected val _toast = MutableSharedFlow<StringModel>()
    val toast: SharedFlow<StringModel> = _toast.asSharedFlow()

    private val ceh = CoroutineExceptionHandler { _, throwable ->
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
        val oldSet = uiState.value.request.positivePromptOptions.associateBy(
            keySelector = {
                it.id
            },
            valueTransform = { it }
        )

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
        val oldSet = uiState.value.request.negativePromptOptions.associateBy(
            keySelector = {
                it.id
            },
            valueTransform = { it }
        )

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

    //
    fun onClickMorePositivePrompt() = scope.launch {

    }

    fun onClickMoreNegativePrompt() = scope.launch {

    }

    fun onClickMoreSDModel() = scope.launch {

    }

    fun onClickMoreLoRaModel() = scope.launch {

    }

    fun onClickMoreEmbeddingsModel() = scope.launch {

    }

    fun onClickChangeSeed(currentSeed: Long?) = sharedEventScope.launch {
        _dialogEvent.emit(SeedChangeDialog(currentSeed = currentSeed))
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

    fun generateImage() = scope.launch {
        showLoading(show = true)
        uiState.value.request.asTxtToImgRequest(
            toastEvent = { message ->
                showToast(message = message)
            }
        )?.let { request ->
            val response = txtToImgRepository.generateImage(request = request)
            when (response.status) {
                "success" -> {
                    response.output.firstOrNull()?.let { result ->
                        _uiState.update {
                            uiState.value.copy(
                                result = result.replaceDomain()
                            )
                        }


                    }
                }

                "processing" -> {
                    fetchQueuedImage(id = response.id)

                }

                else -> {
                    showToast(message = TextString(response.status))
                }
            }
            showLoading(show = false)
        }
    }

    private fun fetchQueuedImage(id: Int) {
        scope.launch {
            delay(3000L)
            val response = txtToImgRepository.fetchQueuedImage(requestId = id.toString())
            if (response.status == "processing") {
                fetchQueuedImage(id = response.id)
            } else if (response.status == "success") {
                response.output.firstOrNull()?.let { output ->
                    _uiState.update {
                        uiState.value.copy(
                            result = output.replaceDomain()
                        )
                    }
                    showLoading(show = false)
                }
            }
        }
    }

    fun temp() {
        _uiState.update {
            uiState.value.copy(
                result = null
            )
        }
    }
}
