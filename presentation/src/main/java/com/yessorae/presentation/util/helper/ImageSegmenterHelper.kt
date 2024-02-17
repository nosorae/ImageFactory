package com.yessorae.presentation.util.helper

import android.graphics.Bitmap
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.vision.imagesegmenter.ImageSegmenterResult
import com.yessorae.presentation.ui.screen.main.inpainting.model.SegmentationLabel

interface ImageSegmentationHelper {
    fun clearImageSegmenter()

    suspend fun segmentImageFile(bitmap: Bitmap): ImageSegmenterResult?

    suspend fun mapMPImageToBitmap(mpImage: MPImage, label: SegmentationLabel): Bitmap

}

