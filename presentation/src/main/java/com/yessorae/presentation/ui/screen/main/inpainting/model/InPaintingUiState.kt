package com.yessorae.presentation.ui.screen.main.inpainting.model

import android.graphics.Bitmap

sealed class InPaintingUiState {
    object Initial: InPaintingUiState()

    data class Image(
        val initialBitmap: Bitmap
    ): InPaintingUiState()

    data class MaskedImage(
        val initialBitmap: Bitmap,
        val maskedBitmap: Bitmap
    ): InPaintingUiState()
}