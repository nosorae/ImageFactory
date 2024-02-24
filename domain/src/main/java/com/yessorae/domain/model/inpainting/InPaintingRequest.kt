package com.yessorae.domain.model.inpainting

import com.yessorae.domain.util.StableDiffusionConstants


data class InPaintingRequest(
    val prompt: String,
    val negativePrompt: String,
    val initImage: String,
    val maskImage: String,
    val guidanceScale: Double,
    val strength: Double, // 0.0~1.0
    val modelId: String,
    val width: Int,
    val height: Int,
    val scheduler: String,
    val steps: Int,
    val loraModel: String?,
    val loraStrength: String?,

    val samples: Int = StableDiffusionConstants.DEFAULT_SAMPLE_COUNT,
    val safetyChecker: String = StableDiffusionConstants.ARG_NO,
    val safetyCheckerType: String = StableDiffusionConstants.DEFAULT_SAFETY_CHECKER_TYPE,
    val enhancePrompt: String = StableDiffusionConstants.ARG_NO,
    val tomesd: String = StableDiffusionConstants.ARG_YES,
    val useKarrasSigmas: String = StableDiffusionConstants.ARG_YES,
    val algorithmType: String = StableDiffusionConstants.DEFAULT_ALGORITHM_TYPE,
    val vae: String? = null,
    val seed: String? = null,
    val webhook: String? = null,
    val trackId: String? = null,
    val clipSkip: Int = StableDiffusionConstants.DEFAULT_CLIP_SKIP,
    val base64: String = StableDiffusionConstants.ARG_NO,
    val temp: String = StableDiffusionConstants.ARG_YES,
    val embeddingsModel: String? = null
)