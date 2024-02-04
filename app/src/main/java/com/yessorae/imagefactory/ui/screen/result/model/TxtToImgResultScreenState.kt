package com.yessorae.imagefactory.ui.screen.result.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.SettingsBackupRestore
import androidx.compose.ui.graphics.vector.ImageVector
import com.yessorae.imagefactory.R
import com.yessorae.domain.model.TxtToImgRequest
import com.yessorae.domain.model.TxtToImgResult
import com.yessorae.domain.model.UpscaleResultModel
import com.yessorae.imagefactory.util.ResString
import com.yessorae.imagefactory.util.StringModel

sealed class TxtToImgResultScreenState(
    open val request: TxtToImgRequest? = null
) {
    object Initial : TxtToImgResultScreenState()
    data class Loading(
        override val request: TxtToImgRequest
    ) : TxtToImgResultScreenState(request = request)

    data class SdSuccess(
        override val request: TxtToImgRequest,
        val sdResult: TxtToImgResult
    ) : TxtToImgResultScreenState(request = request), Result<TxtToImgResult> {
        val ratio = request.width / request.height.toFloat()
        override val result: TxtToImgResult = sdResult
    }

    data class UpscaleLoading(
        override val request: TxtToImgRequest,
        val sdResult: TxtToImgResult
    ) : TxtToImgResultScreenState(request = request), Result<TxtToImgResult> {
        override val result: TxtToImgResult = sdResult
    }

    data class UpscaleSuccess(
        override val request: TxtToImgRequest,
        val sdResult: TxtToImgResult,
        val upscaleResult: UpscaleResultModel
    ) : TxtToImgResultScreenState(request = request), Result<TxtToImgResult> {
        val ratio = request.width / request.height.toFloat()
        val beforeImageUrl: String? = sdResult.imageUrl
        val afterImageUrl: String = upscaleResult.outputUrl
        override val result: TxtToImgResult = sdResult
    }

    data class Processing(
        override val request: TxtToImgRequest,
        val sdResult: TxtToImgResult,
        val message: StringModel
    ) : TxtToImgResultScreenState(request = request)

    data class Error(
        override val request: TxtToImgRequest?,
        val cause: StringModel,
        val backState: TxtToImgResultScreenState,
        val actionType: ActionType = ActionType.NORMAL
    ) : TxtToImgResultScreenState(request = request) {
        enum class ActionType(val text: StringModel, val imageVector: ImageVector) {
            FINISH(ResString(R.string.common_button_back_screen), Icons.Default.ArrowBack),
            NORMAL(ResString(R.string.common_button_back_state), Icons.Default.SettingsBackupRestore)
        }
    }
}

interface Result<out T> {
    val result: T
}
