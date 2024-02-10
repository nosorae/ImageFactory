package com.yessorae.data.remote.stablediffusion.model.response

import com.google.gson.annotations.SerializedName
import com.yessorae.data.local.database.model.ResultMetaDataEntity
import com.yessorae.domain.model.TxtToImgResultMetaData

data class TxtToImgMetaDataResponseDto(
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
    @SerializedName("outdir") val outdir: String?,
    @SerializedName("file_prefix") val filePrefix: String?
)

fun TxtToImgMetaDataResponseDto.asEntity(): ResultMetaDataEntity {
    return ResultMetaDataEntity(
        prompt = this.prompt,
        modelId = this.modelId,
        negativePrompt = this.negativePrompt,
        w = this.w,
        h = this.h,
        guidanceScale = this.guidanceScale,
        seed = this.seed,
        steps = this.steps,
        nSamples = this.nSamples,
        fullUrl = this.fullUrl,
        upscale = this.upscale,
        multiLingual = this.multiLingual,
        panorama = this.panorama,
        selfAttention = this.selfAttention,
        embeddings = this.embeddings,
        lora = this.lora,
        outdir = this.outdir,
        filePrefix = this.filePrefix
    )
}

fun TxtToImgResultMetaData.asEntity(): ResultMetaDataEntity {
    return ResultMetaDataEntity(
        prompt = this.prompt,
        modelId = this.modelId,
        negativePrompt = this.negativePrompt,
        w = this.w,
        h = this.h,
        guidanceScale = this.guidanceScale,
        seed = this.seed,
        steps = this.steps,
        nSamples = this.nSamples,
        fullUrl = this.fullUrl,
        upscale = this.upscale,
        multiLingual = this.multiLingual,
        panorama = this.panorama,
        selfAttention = this.selfAttention,
        embeddings = this.embeddings,
        lora = this.lora,
        outdir = this.outdir,
        filePrefix = this.filePrefix
    )
}

fun TxtToImgMetaDataResponseDto.asDomain(): TxtToImgResultMetaData {
    return TxtToImgResultMetaData(
        prompt = this.prompt,
        modelId = this.modelId,
        negativePrompt = this.negativePrompt,
        w = this.w,
        h = this.h,
        guidanceScale = this.guidanceScale,
        seed = this.seed,
        steps = this.steps,
        nSamples = this.nSamples,
        fullUrl = this.fullUrl,
        upscale = this.upscale,
        multiLingual = this.multiLingual,
        panorama = this.panorama,
        selfAttention = this.selfAttention,
        embeddings = this.embeddings,
        lora = this.lora,
        outdir = this.outdir,
        filePrefix = this.filePrefix
    )
}


fun TxtToImgResultMetaData.asDto(): TxtToImgMetaDataResponseDto {
    return TxtToImgMetaDataResponseDto(
        prompt = this.prompt,
        modelId = this.modelId,
        negativePrompt = this.negativePrompt,
        w = this.w,
        h = this.h,
        guidanceScale = this.guidanceScale,
        seed = this.seed,
        steps = this.steps,
        nSamples = this.nSamples,
        fullUrl = this.fullUrl,
        upscale = this.upscale,
        multiLingual = this.multiLingual,
        panorama = this.panorama,
        selfAttention = this.selfAttention,
        embeddings = this.embeddings,
        lora = this.lora,
        outdir = this.outdir,
        filePrefix = this.filePrefix
    )
}