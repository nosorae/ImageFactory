package com.yessorae.presentation.ui.screen.result.inpainting.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.SettingsBackupRestore
import androidx.compose.ui.graphics.vector.ImageVector
import com.yessorae.domain.model.UpscaleResult
import com.yessorae.domain.model.inpainting.InPaintingRequest
import com.yessorae.domain.model.inpainting.InPaintingResult
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.screen.result.Result

sealed class InPaintingResultScreenState(
    open val request: InPaintingRequest? = null
) {
    object Initial : InPaintingResultScreenState()
    data class Loading(
        override val request: InPaintingRequest
    ) : InPaintingResultScreenState(request = request)

    data class SdSuccess(
        override val request: InPaintingRequest,
        val sdResult: InPaintingResult
    ) : InPaintingResultScreenState(request = request), Result<InPaintingResult> {
        val ratio = request.width / request.height.toFloat()
        override val result: InPaintingResult = sdResult
    }

    data class UpscaleLoading(
        override val request: InPaintingRequest,
        val sdResult: InPaintingResult
    ) : InPaintingResultScreenState(request = request), Result<InPaintingResult> {
        override val result: InPaintingResult = sdResult
    }

    data class UpscaleSuccess(
        override val request: InPaintingRequest,
        val sdResult: InPaintingResult,
        val upscaleResult: UpscaleResult
    ) : InPaintingResultScreenState(request = request), Result<InPaintingResult> {
        val ratio = request.width / request.height.toFloat()
        val beforeImageUrl: String? = sdResult.firstImgUrl
        val afterImageUrl: String = upscaleResult.outputUrl
        override val result: InPaintingResult = sdResult
    }

    data class Processing(
        override val request: InPaintingRequest,
        val sdResult: InPaintingResult,
        val message: String
    ) : InPaintingResultScreenState(request = request)

    data class Error(
        override val request: InPaintingRequest?,
        val cause: String,
        val backState: InPaintingResultScreenState,
        val actionType: ActionType = ActionType.NORMAL
    ) : InPaintingResultScreenState(request = request) {
        enum class ActionType(@StringRes val text: Int, val imageVector: ImageVector) {
            FINISH(R.string.common_button_back_screen, Icons.Default.ArrowBack),
            NORMAL(R.string.common_button_back_state, Icons.Default.SettingsBackupRestore)
        }
    }
}
