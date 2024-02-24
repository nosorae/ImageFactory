package com.yessorae.domain.model.inpainting

data class InPaintingResultMetaData(
    val prompt: String,
    val modelId: String,
    val scheduler: String,
    val safetychecker: String,
    val negativePrompt: String,
    val w: Int,
    val h: Int,
    val guidanceScale: Double,
    val initImage: String,
    val maskImage: String,
    val multiLingual: String,
    val steps: Int,
    val nSamples: Int,
    val fullUrl: String,
    val upscale: String,
    val seed: Long?,
    val outdir: String?,
    val filePrefix: String?
)