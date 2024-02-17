package com.yessorae.presentation.ui.screen.main.inpainting.model

import android.graphics.Bitmap
import com.google.mediapipe.framework.image.MPImage

sealed class InPaintingUiState {
    object Initial: InPaintingUiState()

    data class Image(
        val initialBitmap: Bitmap
    ): InPaintingUiState()

    data class MaskedImage(
        val initialBitmap: Bitmap,
        val maskedBitmap: Bitmap,
        val segmentationLabel: List<SegmentationLabel> = SegmentationLabel.values().toList(),
        val mpImage: MPImage
    ): InPaintingUiState()
}