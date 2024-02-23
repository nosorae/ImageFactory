package com.yessorae.domain.model

data class TxtToImgResultMetaData(
    val prompt: String,
    val modelId: String,
    val negativePrompt: String,
    val scheduler: String,
    val safetychecker: String,
    val w: Int,
    val h: Int,
    val guidanceScale: Double,
    val seed: Long?,
    val steps: Int,
    val nSamples: Int,
    val fullUrl: String,
    val upscale: String,
    val multiLingual: String,
    val panorama: String,
    val selfAttention: String,
    val embeddings: String?,
    val lora: String?,
    val outdir: String?,
    val filePrefix: String?
)
