package com.yessorae.imagefactory.ui.screen.result

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yessorae.common.Logger
import com.yessorae.common.replaceDomain
import com.yessorae.data.repository.TxtToImgHistoryRepository
import com.yessorae.data.repository.TxtToImgRepository
import com.yessorae.data.util.StableDiffusionConstants
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.mapper.TxtToImgHistoryMapper
import com.yessorae.imagefactory.mapper.TxtToImgRequestMapper
import com.yessorae.imagefactory.mapper.TxtToImgResultMapper
import com.yessorae.imagefactory.mapper.UpscaleResultModelMapper
import com.yessorae.imagefactory.ui.model.TxtToImgRequest
import com.yessorae.imagefactory.ui.model.TxtToImgResult
import com.yessorae.imagefactory.ui.navigation.destination.TxtToImgResultDestination
import com.yessorae.imagefactory.ui.screen.result.model.TxtToImgResultScreenState
import com.yessorae.imagefactory.util.ResString
import com.yessorae.imagefactory.util.StringModel
import com.yessorae.imagefactory.util.TextString
import com.yessorae.imagefactory.util.base.BaseScreenViewModel
import com.yessorae.imagefactory.util.handleSdResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TxtToImgResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val txtToImgRepository: TxtToImgRepository,
    private val txtToImgHistoryRepository: TxtToImgHistoryRepository,
    private val txtToImgRequestMapper: TxtToImgRequestMapper,
    private val resultMapper: TxtToImgResultMapper,
    private val upscaleResultModelMapper: UpscaleResultModelMapper,
    private val txtToImgHistoryMapper: TxtToImgHistoryMapper
) : BaseScreenViewModel<TxtToImgResultScreenState>() {
    private val historyId: Int =
        checkNotNull(savedStateHandle[TxtToImgResultDestination.requestIdArg])

    override val initialState: TxtToImgResultScreenState = TxtToImgResultScreenState.Initial

    private val _saveImageEvent = MutableSharedFlow<String>()
    val saveImageEvent = _saveImageEvent.asSharedFlow()

    init {
        initRequest()
    }

    /** init **/

    private fun initRequest() = ioScope.launch {
        val history = txtToImgHistoryMapper.map(
            entity = txtToImgHistoryRepository.getTxtToImgHistory(id = historyId)
        )
        Logger.presentation(message = "id : ${history.id}", true)
        Logger.presentation(message = "${this::class.java.simpleName} - history $history")

        onLoading(request = history.request)

        when {
            history.result != null && history.result.status == StableDiffusionConstants.RESPONSE_PROCESSING -> {
                onSdSuccess(
                    request = history.request,
                    result = history.result
                )
            }

            history.result != null && history.result.status == StableDiffusionConstants.RESPONSE_SUCCESS -> {
                fetchImage(requestId = history.result.id)
            }

            history.result == null -> {
                generateImage(request = history.request)
            }
        }
    }

    /** onClick **/

    fun onClickRetry(currentState: TxtToImgResultScreenState) = ioScope.launch {
        currentState.request?.let { request ->
            val requestId = txtToImgHistoryRepository.insertRequestHistory(
                txtToImgRequestMapper.mapToRequestBody(
                    request = request
                )
            )
            _navigationEvent.emit(
                TxtToImgResultDestination.getRouteWithArgs(requestId = requestId.toInt())
            )
        }
    }

    fun onClickSave(currentState: TxtToImgResultScreenState) = viewModelScope.launch {
        when (currentState) {
            is TxtToImgResultScreenState.UpscaleSuccess -> {
                _saveImageEvent.emit(currentState.afterImageUrl)
            }

            is TxtToImgResultScreenState.SdSuccess -> {
                try {
                    _saveImageEvent.emit(currentState.sdResult.imageUrl!!)
                } catch (e: NullPointerException) {
                    onError(
                        currentState = currentState,
                        cause = TextString(e.toString())
                    )
                }
            }

            else -> {
                showToast(ResString(R.string.common_state_still_load_image))
            }
        }
    }

    fun onClickUpscale(currentState: TxtToImgResultScreenState.SdSuccess, sdResultBitmap: Bitmap?) =
        ioScope.launch {
            try {
                upscaleImage(
                    currentState = currentState,
                    sdResultBitmap = sdResultBitmap
                )
            } catch (e: Exception) {
                onError(
                    currentState = currentState,
                    cause = TextString(e.toString())
                )
            }
        }

    fun onClickBackFromError(backState: TxtToImgResultScreenState) {
        updateState {
            backState
        }
    }

    fun onClickBack() = viewModelScope.launch {
        _backNavigationEvent.emit(Unit)
    }

    fun onSaveComplete() = viewModelScope.launch {
        _toast.emit(ResString(R.string.common_state_complete_save_image))
    }

    fun onSaveFailed(error: Throwable) = viewModelScope.launch {
        _toast.emit(ResString(R.string.common_error_not_download_image_your_country))
        Logger.recordException(error = error)
    }

    /** network request **/

    private suspend fun generateImage(request: TxtToImgRequest) {
        val dto = txtToImgRepository.generateImage(
            request = txtToImgRequestMapper.mapToRequestBody(request = request)
        )

        if (dto.status != StableDiffusionConstants.RESPONSE_ERROR) {
            txtToImgHistoryRepository.updateRequestHistory(
                id = historyId,
                result = dto.copy(
                    output = dto.output.map { it.replaceDomain() }
                ),
                meta = dto.meta
            )
        }

        dto.status.handleSdResponse(
            onSuccess = {
                onSdSuccess(
                    request = resultMapper.mapToMetaDataFromUserRequest(
                        userRequest = request,
                        dto = dto.meta
                    ),
                    result = resultMapper.map(dto = dto)
                )
            },
            onProcessing = {
                onSdProcessing(
                    request = request,
                    response = resultMapper.map(dto = dto)
                )
            },
            onError = {
                txtToImgHistoryRepository.deleteHistory(id = historyId)
                _toast.emit(ResString(R.string.common_response_error))
            }
        )
    }

    private suspend fun fetchImage(requestId: Int) {
        val historyEntity = txtToImgHistoryRepository.fetchQueuedImage(
            id = historyId,
            requestId = requestId.toString()
        )
        val history = txtToImgHistoryMapper.map(
            entity = historyEntity
        )

        history.result?.status?.handleSdResponse(
            onSuccess = {
                updateState {
                    TxtToImgResultScreenState.SdSuccess(
                        request = history.request,
                        sdResult = history.result
                    )
                }
            },
            onProcessing = {
                onSdProcessing(
                    request = history.request,
                    response = history.result
                )
            },
            onError = {
                txtToImgHistoryRepository.deleteHistory(id = historyId)
                _toast.emit(ResString(R.string.common_response_error))
            }
        ) ?: run {
            // todo 예외발생
        }
    }

    private suspend fun upscaleImage(
        currentState: TxtToImgResultScreenState.SdSuccess,
        sdResultBitmap: Bitmap?
    ) {
        sdResultBitmap?.let { bitmap ->
            updateState {
                TxtToImgResultScreenState.UpscaleLoading(
                    request = currentState.request,
                    sdResult = currentState.sdResult
                )
            }

            try {
                val response = txtToImgRepository.upscaleImage(
                    bitmap = bitmap
                )

                response.status.handleSdResponse(
                    onSuccess = {
                        updateState {
                            TxtToImgResultScreenState.UpscaleSuccess(
                                request = currentState.request,
                                sdResult = currentState.sdResult,
                                upscaleResult = upscaleResultModelMapper.map(dto = response)
                            )
                        }
                    },
                    onProcessing = {
                        onError(
                            currentState = currentState,
                            cause = ResString(R.string.common_response_upscale_processing)
                        )
                    },
                    onError = {
                        onError(
                            currentState = currentState,
                            cause = ResString(R.string.common_response_error)
                        )
                    }
                )
            } catch (e: Exception) {
                onError(
                    currentState = currentState,
                    cause = TextString(e.toString())
                )
            }
        } ?: run {
            showToast(ResString(R.string.common_state_still_load_image))
        }
    }

    /** util **/

    private fun onLoading(request: TxtToImgRequest) {
        updateState {
            TxtToImgResultScreenState.Loading(
                request = request
            )
        }
    }

    private fun onSdSuccess(request: TxtToImgRequest, result: TxtToImgResult) {
        updateState {
            TxtToImgResultScreenState.SdSuccess(
                request = request,
                sdResult = result
            )
        }
    }

    private fun onSdProcessing(request: TxtToImgRequest, response: TxtToImgResult) {
        updateState {
            TxtToImgResultScreenState.Processing(
                request = request,
                sdResult = response,
                message = ResString(R.string.common_response_sd_processing)
            )
        }
    }

    private fun onError(currentState: TxtToImgResultScreenState, cause: StringModel) {
        updateState {
            TxtToImgResultScreenState.Error(
                request = currentState.request,
                cause = cause,
                backState = currentState
            )
        }
    }
}
