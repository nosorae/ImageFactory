package com.yessorae.data.model.response

import com.google.gson.annotations.SerializedName

data class TxtToImgDto(
    @SerializedName("status") val status: String,
    @SerializedName("generationTime") val generationTime: Double,
    @SerializedName("id") val id: Int,
    @SerializedName("output") val output: List<String>,
    @SerializedName("meta") val meta: MetaData
)

data class MetaData(
    @SerializedName("prompt") val prompt: String,
    @SerializedName("model_id") val modelId: String,
    @SerializedName("negative_prompt") val negativePrompt: String,
    @SerializedName("W") val w: Int,
    @SerializedName("H") val h: Int,
    @SerializedName("guidance_scale") val guidanceScale: Double,
    @SerializedName("seed") val seed: Long?,
    @SerializedName("steps") val steps: Int,
    @SerializedName("n_samples") val nSamples: Int,
    @SerializedName("full_url") val fullUrl: String,
    @SerializedName("upscale") val upscale: String,
    @SerializedName("multi_lingual") val multiLingual: String,
    @SerializedName("panorama") val panorama: String,
    @SerializedName("self_attention") val selfAttention: String,
    @SerializedName("embeddings") val embeddings: String?,
    @SerializedName("lora") val lora: String?,
    @SerializedName("outdir") val outdir: String,
    @SerializedName("file_prefix") val filePrefix: String
)
