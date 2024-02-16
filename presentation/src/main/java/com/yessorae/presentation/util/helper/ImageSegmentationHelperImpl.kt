package com.yessorae.presentation.util.helper

import android.content.Context
import android.util.Log
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.imagesegmenter.ImageSegmenter
import com.google.mediapipe.tasks.vision.imagesegmenter.ImageSegmenterResult
import com.yessorae.common.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

// TODO:: SR-N 실험중인 코드. Refactoring 필요
class ImageSegmentationHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context
): ImageSegmentationHelper {

    private val imagesegmenter: ImageSegmenter by lazy {
        Logger.presentation("imagesegmenter init 1")
        setupImageSegmenter()
    }

    // Segmenter must be closed when creating a new one to avoid returning results to a
    // non-existent object
    override fun clearImageSegmenter() {
        imagesegmenter.close()
    }

    // TODO:: SR-N Refactoring 특히 필요
    private fun setupImageSegmenter(): ImageSegmenter {
        Logger.presentation("setupImageSegmenter 2")
        return try {
            Logger.presentation("setupImageSegmenter 3")
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
            Logger.presentation("setupImageSegmenter 4")
            Log.e(
                TAG,
                "Image segmenter failed to load model with error: " + e.message
            )
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

    override fun segmentImageFile(mpImage: MPImage): ImageSegmenterResult? {
        Logger.presentation("segmentImageFile")
        return imagesegmenter.segment(mpImage)
    }

    companion object {
        //        const val DELEGATE_CPU = 0
        const val DELEGATE_GPU = 1
//        const val OTHER_ERROR = 0
//        const val GPU_ERROR = 1

        //        const val MODEL_DEEPLABV3 = 0
//        const val MODEL_HAIR_SEGMENTER = 1
//        const val MODEL_SELFIE_SEGMENTER = 2
        const val MODEL_SELFIE_MULTICLASS = 3

        //        const val MODEL_DEEPLABV3_PATH = "deeplabv3.tflite"
//        const val MODEL_HAIR_SEGMENTER_PATH = "hair_segmenter.tflite"
        const val MODEL_SELFIE_MULTICLASS_PATH = "selfie_multiclass_256x256.tflite"
//        const val MODEL_SELFIE_SEGMENTER_PATH = "selfie_segmenter.tflite"

        private const val TAG = "SR-N ImageSegmenterHelper"
    }
}