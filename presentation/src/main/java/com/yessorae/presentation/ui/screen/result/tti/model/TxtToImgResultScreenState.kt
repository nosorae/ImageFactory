package com.yessorae.presentation.ui.screen.result.tti.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.SettingsBackupRestore
import androidx.compose.ui.graphics.vector.ImageVector
import com.yessorae.domain.model.UpscaleResult
import com.yessorae.domain.model.tti.TxtToImgRequest
import com.yessorae.domain.model.tti.TxtToImgResult
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.screen.result.Result

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
        val upscaleResult: UpscaleResult
    ) : TxtToImgResultScreenState(request = request), Result<TxtToImgResult> {
        val ratio = request.width / request.height.toFloat()
        val beforeImageUrl: String? = sdResult.firstImgUrl
        val afterImageUrl: String = upscaleResult.outputUrl
        override val result: TxtToImgResult = sdResult
    }

    data class Processing(
        override val request: TxtToImgRequest,
        val sdResult: TxtToImgResult,
        val message: String
    ) : TxtToImgResultScreenState(request = request)

    data class Error(
        override val request: TxtToImgRequest?,
        val cause: String,
        val backState: TxtToImgResultScreenState,
        val actionType: ActionType = ActionType.NORMAL
    ) : TxtToImgResultScreenState(request = request) {
        enum class ActionType(@StringRes val text: Int, val imageVector: ImageVector) {
            FINISH(R.string.common_button_back_screen, Icons.Default.ArrowBack),
            NORMAL(R.string.common_button_back_state, Icons.Default.SettingsBackupRestore)
        }
    }
}

