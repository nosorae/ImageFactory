package com.yessorae.presentation.ui.screen.result

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.yessorae.common.Logger
import com.yessorae.common.bitmapToByteArray
import com.yessorae.domain.model.tti.TxtToImgHistory
import com.yessorae.domain.util.StableDiffusionConstants
import com.yessorae.domain.model.tti.TxtToImgRequest
import com.yessorae.domain.model.tti.TxtToImgResult
import com.yessorae.domain.usecase.tti.GetTxtToImgHistoryUseCase
import com.yessorae.domain.usecase.tti.InsertTxtToImgHistoryUseCase
import com.yessorae.domain.usecase.tti.RequestFetchProcessingTxtToImgUseCase
import com.yessorae.domain.usecase.tti.RequestTxtToImgUseCase
import com.yessorae.domain.usecase.UpscaleImgUseCase
import com.yessorae.domain.util.ProcessingErrorException
import com.yessorae.presentation.R
import com.yessorae.presentation.navigation.destination.TxtToImgResultDestination
import com.yessorae.presentation.ui.screen.result.model.TxtToImgResultScreenState
import com.yessorae.presentation.util.helper.StringResourceHelper
import com.yessorae.presentation.util.base.BaseScreenViewModel
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
class TxtToImgResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTxtToImgHistoryUseCase: GetTxtToImgHistoryUseCase,
    private val insertTxtToImgHistoryUseCase: InsertTxtToImgHistoryUseCase,
    private val requestTxtToImgUseCase: RequestTxtToImgUseCase,
    private val upscaleImgUseCase: UpscaleImgUseCase,
    private val requestFetchProcessingTxtToImgUseCase: RequestFetchProcessingTxtToImgUseCase,
    private val stringResourceHelper: StringResourceHelper
) : BaseScreenViewModel() {
    private val historyId: Int =
        checkNotNull(savedStateHandle[TxtToImgResultDestination.requestIdArg])

    private val _state =
        MutableStateFlow<TxtToImgResultScreenState>(TxtToImgResultScreenState.Initial)
    val state: StateFlow<TxtToImgResultScreenState> = _state.asStateFlow().onSubscription {
        initRequest()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = TxtToImgResultScreenState.Initial
    )

    private val _saveImageEvent = MutableSharedFlow<String>()
    val saveImageEvent = _saveImageEvent.asSharedFlow()


    /** init **/
    private fun initRequest() = ioScope.launch {
        val history = getTxtToImgHistoryUseCase(id = historyId)
        Logger.temp("TxtToImgResultViewModel initRequest : ${history}")
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
    fun onClickRetry(currentState: TxtToImgResultScreenState) = ioScope.launch {
        currentState.request?.let { request ->
            val requestId =
                insertTxtToImgHistoryUseCase(request = TxtToImgHistory(request = request))

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
                    cause = e.toString()
                )
            }
        }

    fun onClickBackFromError(backState: TxtToImgResultScreenState) {
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

    private suspend fun generateImage(request: TxtToImgRequest) {
        fun onFail(message: String) = ioScope.launch {
            onError(
                currentState = TxtToImgResultScreenState.Initial,
                cause = message,
                actionType = TxtToImgResultScreenState.Error.ActionType.FINISH
            )
        }

        try {
            val result = requestTxtToImgUseCase(historyId)
            Logger.temp("TxtToImgResultViewModel result: $result")
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
                currentState = TxtToImgResultScreenState.Initial,
                cause = message,
                actionType = TxtToImgResultScreenState.Error.ActionType.FINISH
            )
        }

        try {
            val newTxtToImgHistory = requestFetchProcessingTxtToImgUseCase(
                historyId = historyId,
                requestId = requestId.toString()
            )
            val result = newTxtToImgHistory.result!!

            when (result.status) {
                StableDiffusionConstants.RESPONSE_SUCCESS -> {
                    _state.update {
                        TxtToImgResultScreenState.SdSuccess(
                            request = newTxtToImgHistory.request,
                            sdResult = result
                        )
                    }
                }

                StableDiffusionConstants.RESPONSE_PROCESSING -> {
                    onSdProcessing(
                        request = newTxtToImgHistory.request,
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
        currentState: TxtToImgResultScreenState.SdSuccess,
        sdResultBitmap: Bitmap?
    ) {
        sdResultBitmap?.let { bitmap ->
            _state.update {
                TxtToImgResultScreenState.UpscaleLoading(
                    request = currentState.request,
                    sdResult = currentState.sdResult
                )
            }

            try {
                val response = upscaleImgUseCase(bitmap = bitmap.bitmapToByteArray()!!)

                when (response.status) {
                    StableDiffusionConstants.RESPONSE_SUCCESS -> {
                        TxtToImgResultScreenState.UpscaleSuccess(
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

    private fun onLoading(request: TxtToImgRequest) {
        _state.update {
            TxtToImgResultScreenState.Loading(
                request = request
            )
        }
    }

    private fun onSdSuccess(request: TxtToImgRequest, result: TxtToImgResult) {
        _state.update {
            TxtToImgResultScreenState.SdSuccess(
                request = request,
                sdResult = result
            )
        }
    }

    private fun onSdProcessing(request: TxtToImgRequest, response: TxtToImgResult) {
        _state.update {
            TxtToImgResultScreenState.Processing(
                request = request,
                sdResult = response,
                message = stringResourceHelper.getString(R.string.common_response_sd_processing)
            )
        }
    }

    private fun onError(
        currentState: TxtToImgResultScreenState,
        cause: String,
        actionType: TxtToImgResultScreenState.Error.ActionType = TxtToImgResultScreenState.Error.ActionType.NORMAL
    ) {
        _state.update {
            TxtToImgResultScreenState.Error(
                request = currentState.request,
                cause = cause,
                backState = currentState,
                actionType = actionType
            )
        }
    }
}
