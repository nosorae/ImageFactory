package com.yessorae.domain.model

import com.yessorae.domain.util.Constants

data class TxtToImgRequest(
    val prompt: String,
    val modelId: String,
    val negativePrompt: String,
    val width: Int,
    val height: Int,
    val guidanceScale: Double,
    val seed: Long?,
    val steps: Int,
    val nSamples: Int,
    val upscale: String,
    val multiLingual: String,
    val panorama: String,
    val selfAttention: String,
    val embeddings: String?,
    val lora: String?,
    val loraStrength: String?,
    val scheduler: String,
    val safetyChecker: String = Constants.ARG_NO,
    val enhancePrompt: String = Constants.ARG_NO
)
