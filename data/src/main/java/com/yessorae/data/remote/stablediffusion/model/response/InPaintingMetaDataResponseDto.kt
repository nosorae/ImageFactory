package com.yessorae.data.remote.stablediffusion.model.response

import com.google.gson.annotations.SerializedName
import com.yessorae.data.local.database.model.inpainting.InPaintingResultMetaDataEntity
import com.yessorae.domain.model.inpainting.InPaintingResultMetaData

data class InPaintingMetaDataResponseDto(
    @SerializedName("prompt") val prompt: String,
    @SerializedName("model_id") val modelId: String,
    @SerializedName("scheduler") val scheduler: String,
    @SerializedName("safetychecker") val safetychecker: String,
    @SerializedName("negative_prompt") val negativePrompt: String,
    @SerializedName("W") val w: Int,
    @SerializedName("H") val h: Int,
    @SerializedName("guidance_scale") val guidanceScale: Double,
    @SerializedName("init_image") val initImage: String,
    @SerializedName("mask_image") val maskImage: String,
    @SerializedName("multi_lingual") val multiLingual: String,
    @SerializedName("steps") val steps: Int,
    @SerializedName("n_samples") val nSamples: Int,
    @SerializedName("full_url") val fullUrl: String,
    @SerializedName("upscale") val upscale: String,
    @SerializedName("seed") val seed: Long?,
    @SerializedName("outdir") val outdir: String?,
    @SerializedName("file_prefix") val filePrefix: String?
)

fun InPaintingMetaDataResponseDto.asEntity(): InPaintingResultMetaDataEntity {
    return InPaintingResultMetaDataEntity(
        prompt = prompt,
        modelId = modelId,
        scheduler = scheduler,
        safetychecker = safetychecker,
        negativePrompt = negativePrompt,
        w = w,
        h = h,
        guidanceScale = guidanceScale,
        initImage = initImage,
        maskImage = maskImage,
        multiLingual = multiLingual,
        steps = steps,
        nSamples = nSamples,
        fullUrl = fullUrl,
        upscale = upscale,
        seed = seed,
        outdir = outdir,
        filePrefix = filePrefix
    )
}

fun InPaintingMetaDataResponseDto.asDomain(): InPaintingResultMetaData {
    return InPaintingResultMetaData(
        prompt = prompt,
        modelId = modelId,
        scheduler = scheduler,
        safetychecker = safetychecker,
        negativePrompt = negativePrompt,
        w = w,
        h = h,
        guidanceScale = guidanceScale,
        initImage = initImage,
        maskImage = maskImage,
        multiLingual = multiLingual,
        steps = steps,
        nSamples = nSamples,
        fullUrl = fullUrl,
        upscale = upscale,
        seed = seed,
        outdir = outdir,
        filePrefix = filePrefix
    )
}
