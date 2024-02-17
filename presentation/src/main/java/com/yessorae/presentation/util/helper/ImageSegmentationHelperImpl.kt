package com.yessorae.presentation.util.helper

import android.content.Context
import android.graphics.Bitmap
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.ByteBufferExtractor
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.imagesegmenter.ImageSegmenter
import com.google.mediapipe.tasks.vision.imagesegmenter.ImageSegmenterResult
import com.yessorae.presentation.ui.screen.main.inpainting.labelColors
import com.yessorae.presentation.ui.screen.main.inpainting.model.SegmentationLabel
import com.yessorae.presentation.ui.screen.main.inpainting.toAlphaColor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

// TODO:: SR-N 실험중인 코드. Refactoring 필요
class ImageSegmentationHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ImageSegmentationHelper {

    private val imageSegmenter: ImageSegmenter by lazy {
        setupImageSegmenter()
    }

    // Segmenter must be closed when creating a new one to avoid returning results to a
    // non-existent object
    override fun clearImageSegmenter() {
        imageSegmenter.close()
    }

    private fun setupImageSegmenter(): ImageSegmenter {
        return try {
            ImageSegmenter.createFromOptions(
                context,
                ImageSegmenter.ImageSegmenterOptions.builder()
                    .setRunningMode(RunningMode.IMAGE)
                    .setBaseOptions(
                        BaseOptions.builder()
                            .setDelegate(Delegate.GPU)
                            .setModelAssetPath(MODEL_SELFIE_MULTICLASS_PATH)
                            .build()
                    )
                    .setOutputCategoryMask(true)
                    .setOutputConfidenceMasks(false)
                    .build()
            )
        } catch (e: RuntimeException) {
            // This occurs if the model being used does not support GPU
            ImageSegmenter.createFromOptions(
                context,
                ImageSegmenter.ImageSegmenterOptions.builder()
                    .setRunningMode(RunningMode.IMAGE)
                    .setBaseOptions(
                        BaseOptions.builder()
                            .setDelegate(Delegate.CPU)
                            .setModelAssetPath(MODEL_SELFIE_MULTICLASS_PATH)
                            .build()
                    )
                    .setOutputCategoryMask(true)
                    .setOutputConfidenceMasks(false)
                    .build()
            )
        }
    }

    override suspend fun segmentImageFile(bitmap: Bitmap): ImageSegmenterResult? = suspendCancellableCoroutine {
        val mpImage = BitmapImageBuilder(bitmap).build()
        it.resume(imageSegmenter.segment(mpImage))
    }

    override suspend fun mapMPImageToBitmap(mpImage: MPImage, label: SegmentationLabel): Bitmap =
        suspendCancellableCoroutine { continuation ->
            val byteBuffer = ByteBufferExtractor.extract(mpImage)

            val pixels = IntArray(byteBuffer.capacity())

            if (label == SegmentationLabel.ALL) {
                for (i in pixels.indices) {
                    val index = byteBuffer.get(i).toUInt() % 20U
                    val color = labelColors[index.toInt()].toAlphaColor()
                    pixels[i] = color
                }

            } else {
                for (i in pixels.indices) {
                    val index = byteBuffer.get(i).toUInt() % 20U
                    val color = if (index == label.index) {
                        labelColors[index.toInt()].toAlphaColor()
                    } else {
                        0
                    }
                    pixels[i] = color
                }
            }

            continuation.resume(
                Bitmap.createBitmap(
                    pixels,
                    mpImage.width,
                    mpImage.height,
                    Bitmap.Config.ARGB_8888
                )
            )
        }


    companion object {
        const val MODEL_SELFIE_MULTICLASS_PATH = "selfie_multiclass_256x256.tflite"
        const val CATEGORY_BACKGROUND = 0U
        const val CATEGORY_HAIR = 1U
        const val CATEGORY_BODY_SKIN = 2U
        const val CATEGORY_FACE_SKIN = 3U
        const val CATEGORY_CLOTHES = 4U
        const val CATEGORY_ETC = 5U
    }
}