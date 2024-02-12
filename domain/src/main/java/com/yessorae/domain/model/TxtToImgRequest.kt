package com.yessorae.domain.model

import com.yessorae.domain.util.StableDiffusionConstants.ARG_NO
import com.yessorae.domain.util.StableDiffusionConstants.DEFAULT_HEIGHT
import com.yessorae.domain.util.StableDiffusionConstants.DEFAULT_SAMPLE_COUNT
import com.yessorae.domain.util.StableDiffusionConstants.DEFAULT_WIDTH

data class TxtToImgRequest(
    val prompt: String,
    val negativePrompt: String,
    val guidanceScale: Double,
    val modelId: String,
    val lora: String?,
    val loraStrength: String?,
    val embeddings: String?,
    val scheduler: String,
    val steps: Int,
    val multiLingual: String,

    val width: Int = DEFAULT_WIDTH,
    val height: Int = DEFAULT_HEIGHT,
    val seed: Long? = null,
    // Number of images to be returned in response. The maximum value is 4.
    val nSamples: Int = DEFAULT_SAMPLE_COUNT,
    // If you want a high quality image, set this parameter to "yes". In this case the image generation will take more time.
    // TODO:: SR-N 이미지 잘 안나온다 싶으면 YES로 변경
    val selfAttention: String = ARG_NO,
    // Set this parameter to "2" if you want to upscale the given image resolution two times (2x), options:: 1, 2, 3
    val upscale: String = ARG_NO,
    val panorama: String = ARG_NO,
    val safetyChecker: String = ARG_NO,
    val enhancePrompt: String = ARG_NO
)
