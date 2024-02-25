package com.yessorae.presentation.ui.screen.result.inpainting

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yessorae.common.Logger
import com.yessorae.common.bitmapToByteArray
import com.yessorae.domain.model.inpainting.InPaintingRequest
import com.yessorae.domain.model.inpainting.InPaintingResult
import com.yessorae.domain.usecase.UpscaleImgUseCase
import com.yessorae.domain.usecase.inpainting.GetInPaintingHistoryUseCase
import com.yessorae.domain.usecase.inpainting.InsertInPaintingHistoryUseCase
import com.yessorae.domain.usecase.inpainting.RequestFetchProcessingInPaintingUseCase
import com.yessorae.domain.usecase.inpainting.RequestInPaintingUseCase
import com.yessorae.domain.util.ProcessingErrorException
import com.yessorae.domain.util.StableDiffusionConstants
import com.yessorae.presentation.R
import com.yessorae.presentation.navigation.destination.InPaintingResultDestination
import com.yessorae.presentation.ui.screen.result.inpainting.model.InPaintingResultScreenState
import com.yessorae.presentation.util.base.BaseScreenViewModel
import com.yessorae.presentation.util.helper.StringResourceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InPaintingResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getInPaintingHistoryUseCase: GetInPaintingHistoryUseCase,
    private val insertInPaintingHistoryUseCase: InsertInPaintingHistoryUseCase,
    private val requestInPaintingUseCase: RequestInPaintingUseCase,
    private val requestFetchProcessingInPaintingUseCase: RequestFetchProcessingInPaintingUseCase,
    private val upscaleImgUseCase: UpscaleImgUseCase,
    private val stringResourceHelper: StringResourceHelper
) : BaseScreenViewModel() {
    private val historyId: Int =
        checkNotNull(savedStateHandle[InPaintingResultDestination.requestIdArg])

    private val _state =
        MutableStateFlow<InPaintingResultScreenState>(InPaintingResultScreenState.Initial)
    val state: StateFlow<InPaintingResultScreenState> = _state.asStateFlow().onSubscription {
        initRequest()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = InPaintingResultScreenState.Initial
    )

    private val _saveImageEvent = MutableSharedFlow<String>()
    val saveImageEvent = _saveImageEvent.asSharedFlow()

    /** init **/
    private fun initRequest() = ioScope.launch {
        val history = getInPaintingHistoryUseCase(id = historyId)
        Logger.temp("InPaintingResultViewModel initRequest : ${history}")
        val historyResult = history.result

        onLoading(request = history.request)

        when {
            historyResult == null -> {
                generateImage(request = history.request)
            }

            historyResult.status == StableDiffusionConstants.RESPONSE_PROCESSING -> {
                fetchImage(requestId = historyResult.id)
            }

            historyResult.status == StableDiffusionConstants.RESPONSE_SUCCESS -> {
                onSdSuccess(
                    request = history.request,
                    result = historyResult
                )
            }
        }
    }

    /** onClick **/
    fun onClickRetry(currentState: InPaintingResultScreenState) = ioScope.launch {
        currentState.request?.let { request ->
            val requestId =
                insertInPaintingHistoryUseCase(request = request)

            _navigationEvent.emit(
                InPaintingResultDestination.getRouteWithArgs(requestId = requestId.toInt())
            )
        }
    }

    fun onClickSave(currentState: InPaintingResultScreenState) = viewModelScope.launch {
        when (currentState) {
            is InPaintingResultScreenState.UpscaleSuccess -> {
                _saveImageEvent.emit(currentState.afterImageUrl)
            }

            is InPaintingResultScreenState.SdSuccess -> {
                try {
                    _saveImageEvent.emit(currentState.sdResult.firstImgUrl!!)
                } catch (e: NullPointerException) {
                    onError(
                        currentState = currentState,
                        cause = e.toString()
                    )
                }
            }

            else -> {
                showToast(stringResourceHelper.getString(R.string.common_state_still_load_image))
            }
        }
    }

    fun onClickUpscale(
        currentState: InPaintingResultScreenState.SdSuccess,
        sdResultBitmap: Bitmap?
    ) =
        ioScope.launch {
            try {
                upscaleImage(
                    currentState = currentState,
                    sdResultBitmap = sdResultBitmap
                )
            } catch (e: Exception) {
                onError(
                    currentState = currentState,
                    cause = e.toString()
                )
            }
        }

    fun onClickBackFromError(backState: InPaintingResultScreenState) {
        _state.update {
            backState
        }
    }

    fun onClickBack() = viewModelScope.launch {
        _backNavigationEvent.emit(Unit)
    }

    fun onSaveComplete() = viewModelScope.launch {
        _toast.emit(stringResourceHelper.getString(R.string.common_state_complete_save_image))
    }

    fun onSaveFailed(error: Throwable) = viewModelScope.launch {
        _toast.emit(stringResourceHelper.getString(R.string.common_error_not_download_image_your_country))
        Logger.recordException(error = error)
    }


    /** network request **/
    private suspend fun generateImage(request: InPaintingRequest) {
        fun onFail(message: String) = ioScope.launch {
            onError(
                currentState = InPaintingResultScreenState.Initial,
                cause = message,
                actionType = InPaintingResultScreenState.Error.ActionType.FINISH
            )
        }

        try {
            val result = requestInPaintingUseCase(historyId)
            Logger.temp("InPaintingResultViewModel result: $result")
            when (result.status) {
                StableDiffusionConstants.RESPONSE_SUCCESS -> {
                    onSdSuccess(
                        request = request,
                        result = result
                    )
                }

                StableDiffusionConstants.RESPONSE_PROCESSING -> {
                    onSdProcessing(
                        request = request,
                        response = result
                    )
                }

                else -> {
                    // do nothing
                }
            }
        } catch (e: Exception) {
            when (e) {
                is ProcessingErrorException -> {
                    onFail(
                        message = stringResourceHelper.getString(
                            R.string.common_title_error_cause,
                            e.message
                        )
                    )
                }

                else -> {
                    onFail(
                        message = stringResourceHelper.getString(
                            R.string.common_response_unknown_error
                        )
                    )
                }
            }
        }
    }

    private suspend fun fetchImage(requestId: Int) {
        fun onFail(message: String) {
            onError(
                currentState = InPaintingResultScreenState.Initial,
                cause = message,
                actionType = InPaintingResultScreenState.Error.ActionType.FINISH
            )
        }

        try {
            val newInPaintingHistory = requestFetchProcessingInPaintingUseCase(
                historyId = historyId,
                requestId = requestId.toString()
            )
            val result = newInPaintingHistory.result!!

            when (result.status) {
                StableDiffusionConstants.RESPONSE_SUCCESS -> {
                    _state.update {
                        InPaintingResultScreenState.SdSuccess(
                            request = newInPaintingHistory.request,
                            sdResult = result
                        )
                    }
                }

                StableDiffusionConstants.RESPONSE_PROCESSING -> {
                    onSdProcessing(
                        request = newInPaintingHistory.request,
                        response = result
                    )
                }

                else -> {
                    onFail(message = stringResourceHelper.getString(R.string.common_response_unknown_error))
                }
            }
        } catch (e: Exception) {
            when (e) {
                is ProcessingErrorException -> {
                    onFail(
                        message = stringResourceHelper.getString(
                            R.string.common_title_error_cause,
                            e.message
                        )
                    )
                }

                else -> {
                    onFail(message = stringResourceHelper.getString(R.string.common_response_unknown_error))
                }
            }
            return
        }
    }

    private suspend fun upscaleImage(
        currentState: InPaintingResultScreenState.SdSuccess,
        sdResultBitmap: Bitmap?
    ) {
        sdResultBitmap?.let { bitmap ->
            _state.update {
                InPaintingResultScreenState.UpscaleLoading(
                    request = currentState.request,
                    sdResult = currentState.sdResult
                )
            }

            try {
                val response = upscaleImgUseCase(bitmap = bitmap.bitmapToByteArray()!!)

                when (response.status) {
                    StableDiffusionConstants.RESPONSE_SUCCESS -> {
                        InPaintingResultScreenState.UpscaleSuccess(
                            request = currentState.request,
                            sdResult = currentState.sdResult,
                            upscaleResult = response
                        )
                    }

                    StableDiffusionConstants.RESPONSE_PROCESSING -> {
                        onError(
                            currentState = currentState,
                            cause = stringResourceHelper.getString(R.string.common_response_upscale_processing)
                        )
                    }

                    else -> {
                        // do nothing
                    }
                }
            } catch (e: Exception) {
                when (e) {
                    is ProcessingErrorException -> {
                        onError(
                            currentState = currentState,
                            cause = stringResourceHelper.getString(R.string.common_response_unknown_error)
                        )
                    }

                    else -> {
                        onError(
                            currentState = currentState,
                            cause = e.toString()
                        )
                    }
                }
            }
        } ?: run {
            showToast(stringResourceHelper.getString(R.string.common_state_still_load_image))
        }
    }


    /** util **/

    private fun onLoading(request: InPaintingRequest) {
        _state.update {
            InPaintingResultScreenState.Loading(
                request = request
            )
        }
    }

    private fun onSdSuccess(
        request: InPaintingRequest,
        result: InPaintingResult
    ) {
        _state.update {
            InPaintingResultScreenState.SdSuccess(
                request = request,
                sdResult = result
            )
        }
    }

    private fun onSdProcessing(
        request: InPaintingRequest,
        response: InPaintingResult
    ) {
        _state.update {
            InPaintingResultScreenState.Processing(
                request = request,
                sdResult = response,
                message = stringResourceHelper.getString(R.string.common_response_sd_processing)
            )
        }
    }

    private fun onError(
        currentState: InPaintingResultScreenState,
        cause: String,
        actionType: InPaintingResultScreenState.Error.ActionType = InPaintingResultScreenState.Error.ActionType.NORMAL
    ) {
        _state.update {
            InPaintingResultScreenState.Error(
                request = currentState.request,
                cause = cause,
                backState = currentState,
                actionType = actionType
            )
        }
    }
}