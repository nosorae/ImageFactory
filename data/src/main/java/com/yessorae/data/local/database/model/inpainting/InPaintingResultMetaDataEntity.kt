package com.yessorae.data.local.database.model.inpainting

import androidx.room.ColumnInfo
import com.yessorae.domain.model.inpainting.InPaintingResultMetaData

data class InPaintingResultMetaDataEntity(
    @ColumnInfo("prompt")
    val prompt: String,
    @ColumnInfo("model_id")
    val modelId: String,
    @ColumnInfo("scheduler")
    val scheduler: String,
    @ColumnInfo("safetychecker")
    val safetychecker: String,
    @ColumnInfo("negative_prompt")
    val negativePrompt: String,
    @ColumnInfo("W")
    val w: Int,
    @ColumnInfo("H")
    val h: Int,
    @ColumnInfo("guidance_scale")
    val guidanceScale: Double,
    @ColumnInfo("init_image")
    val initImage: String,
    @ColumnInfo("mask_image")
    val maskImage: String,
    @ColumnInfo("multi_lingual")
    val multiLingual: String,
    @ColumnInfo("steps")
    val steps: Int,
    @ColumnInfo("n_samples")
    val nSamples: Int,
    @ColumnInfo("full_url")
    val fullUrl: String,
    @ColumnInfo("upscale")
    val upscale: String,
    @ColumnInfo("seed")
    val seed: Long?,
    @ColumnInfo("outdir")
    val outdir: String?,
    @ColumnInfo("file_prefix")
    val filePrefix: String?
)

fun InPaintingResultMetaDataEntity.asDomainModel(): InPaintingResultMetaData {
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

fun InPaintingResultMetaData.asEntity(): InPaintingResultMetaDataEntity {
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