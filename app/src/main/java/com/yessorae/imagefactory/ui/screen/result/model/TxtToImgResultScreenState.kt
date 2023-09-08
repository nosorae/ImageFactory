package com.yessorae.imagefactory.ui.screen.result.model

import com.yessorae.imagefactory.util.StringModel

sealed class TxtToImgResultScreenState(
    open val request: TxtToImgRequest? = null,
    val order: Int
) {
    object Initial : TxtToImgResultScreenState(order = 0)
    data class Loading(
        override val request: TxtToImgRequest,
    ) : TxtToImgResultScreenState(request = request, order = 1)

    data class SdSuccess(
        override val request: TxtToImgRequest,
        val sdResult: TxtToImgResultModel
    ) : TxtToImgResultScreenState(request = request, order = 2), Result<TxtToImgResultModel> {
        val ratio = request.width / request.height.toFloat()
        override val result: TxtToImgResultModel = sdResult
    }

    data class UpscaleLoading(
        override val request: TxtToImgRequest,
        val sdResult: TxtToImgResultModel
    ) : TxtToImgResultScreenState(request = request, order = 3), Result<TxtToImgResultModel> {
        override val result: TxtToImgResultModel = sdResult
    }

    data class UpscaleSuccess(
        override val request: TxtToImgRequest,
        val sdResult: TxtToImgResultModel,
        val upscaleResult: UpscaleResultModel
    ) : TxtToImgResultScreenState(request = request, order = 4), Result<TxtToImgResultModel> {
        val ratio = request.width / request.height.toFloat()
        val beforeImageUrl: String? = sdResult.imageUrl
        val afterImageUrl: String = upscaleResult.outputUrl
        override val result: TxtToImgResultModel = sdResult
    }

    data class Error(
        override val request: TxtToImgRequest?,
        val cause: StringModel,
        val backState: TxtToImgResultScreenState
    ) : TxtToImgResultScreenState(request = request, order = Int.MAX_VALUE)
}

interface Result<T> {
    val result: T
}
