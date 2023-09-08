package com.yessorae.imagefactory.ui.screen.result

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.yessorae.common.Logger
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequestBody
import com.yessorae.data.repository.TxtToImgRepository
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.mapper.TxtToImgRequestMapper
import com.yessorae.imagefactory.mapper.TxtToImgResultMapper
import com.yessorae.imagefactory.mapper.UpscaleResultModelMapper
import com.yessorae.imagefactory.ui.screen.result.model.TxtToImgResultScreenState
import com.yessorae.imagefactory.util.ResString
import com.yessorae.imagefactory.util.StringModel
import com.yessorae.imagefactory.util.TextString
import com.yessorae.imagefactory.util.base.BaseScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TxtToImgResultViewModel @Inject constructor(
    private val txtToImgRepository: TxtToImgRepository,
    private val txtToImgRequestMapper: TxtToImgRequestMapper,
    private val resultMapper: TxtToImgResultMapper,
    private val upscaleResultModelMapper: UpscaleResultModelMapper
) : BaseScreenViewModel<TxtToImgResultScreenState>() {
    override val initialState: TxtToImgResultScreenState = TxtToImgResultScreenState.Initial

    private var lastRequestBody: TxtToImgRequestBody? = null

    private val _saveImageEvent = MutableSharedFlow<String>()
    val saveImageEvent = _saveImageEvent.asSharedFlow()

    init {
        initRequest()
    }

    /** init **/

    private fun initRequest() = ioScope.launch {
        txtToImgRepository.getLastRequest().collectLatest { requestBody ->
            Logger.presentation("initRequest requestBody : $requestBody")
            requestBody?.let {
                lastRequestBody = requestBody
                generateImage(requestBody = requestBody)
            }
        }
    }

    /** onClick **/

    fun onClickRetry(currentState: TxtToImgResultScreenState) = ioScope.launch {
        when (currentState) {
            is TxtToImgResultScreenState.SdSuccess,
            is TxtToImgResultScreenState.UpscaleSuccess -> {
                try {
                    generateImage(
                        requestBody = txtToImgRequestMapper.mapToRequestBody(
                            request = currentState.request!!
                        )
                    )
                } catch (e: NullPointerException) {
                    onError(
                        currentState = currentState,
                        cause = TextString(e.toString())
                    )
                } catch (e: Exception) {
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
            upscaleImage(
                currentState = currentState,
                sdResultBitmap = sdResultBitmap
            )
        }

    fun onClickBackFromError(backState: TxtToImgResultScreenState) {
        updateState {
            backState
        }
    }

    /** onComplete **/

    fun onCompleteLoadGeneratedImage(state: TxtToImgResultScreenState.SdSuccess, bitmap: Bitmap) {
        // todo firestorage 에 이미지 저장하고 url받아와서, 요청정보와 결과 이미지 url db 에 저장
    }

    fun onSaveComplete() = viewModelScope.launch {
        _toast.emit(ResString(R.string.common_state_complete_save_image))
    }

    fun onSaveFailed(error: Throwable) = viewModelScope.launch {
        _toast.emit(ResString(R.string.common_error_not_download_image_your_country))
        Logger.recordException(error = error)
    }


    /** network request **/

    private suspend fun generateImage(requestBody: TxtToImgRequestBody) {
        val request = txtToImgRequestMapper.map(requestBody = requestBody)
        updateState {
            TxtToImgResultScreenState.Loading(
                request = request
            )
        }

        val dto = txtToImgRepository.generateImage(request = requestBody)
        updateState {
            TxtToImgResultScreenState.SdSuccess(
                request = resultMapper.mapToMetaDataFromUserRequest(
                    userRequest = request,
                    dto = dto.meta
                ),
                sdResult = resultMapper.map(dto = dto)
            )
        }
    }

    private suspend fun upscaleImage(
        currentState: TxtToImgResultScreenState.SdSuccess,
        sdResultBitmap: Bitmap?
    ) {
        updateState {
            TxtToImgResultScreenState.UpscaleLoading(
                request = currentState.request,
                sdResult = currentState.sdResult
            )
        }

        sdResultBitmap?.let { bitmap ->
            try {
                val response = txtToImgRepository.upscaleImage(
                    bitmap = bitmap
                )
                updateState {
                    TxtToImgResultScreenState.UpscaleSuccess(
                        request = currentState.request,
                        sdResult = currentState.sdResult,
                        upscaleResult = upscaleResultModelMapper.map(dto = response)
                    )
                }
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










