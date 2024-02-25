package com.yessorae.presentation.ui.screen.main.inpainting.model

import android.graphics.Bitmap
import androidx.compose.runtime.Stable
import com.google.mediapipe.framework.image.MPImage

sealed class InPaintingUiState {
    @Stable
    object Initial : InPaintingUiState()

    @Stable
    object Loading : InPaintingUiState()

    //    data class Image(
//        val initialBitmap: Bitmap
//    ): InPaintingUiState()
    @Stable
    data class MaskedImage(
        val initialBitmap: Bitmap,
        val maskedBitmap: Bitmap,
        val segmentationLabel: List<SegmentationLabel> = SegmentationLabel.values().toList(),
        val mpImage: MPImage
    ) : InPaintingUiState()
}