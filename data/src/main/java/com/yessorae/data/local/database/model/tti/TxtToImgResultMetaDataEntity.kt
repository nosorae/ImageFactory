package com.yessorae.data.local.database.model.tti

import androidx.room.ColumnInfo
import com.yessorae.data.remote.stablediffusion.model.response.TxtToImgMetaDataResponseDto
import com.yessorae.domain.model.tti.TxtToImgResultMetaData

data class TxtToImgResultMetaDataEntity(
    @ColumnInfo(name = "prompt")
    val prompt: String,
    @ColumnInfo(name = "model_id")
    val modelId: String,
    @ColumnInfo(name = "negative_prompt")
    val negativePrompt: String,
    @ColumnInfo("scheduler")
    val scheduler: String,
    @ColumnInfo("safetychecker")
    val safetychecker: String,
    @ColumnInfo(name = "W")
    val w: Int,
    @ColumnInfo(name = "H")
    val h: Int,
    @ColumnInfo(name = "guidance_scale")
    val guidanceScale: Double,
    @ColumnInfo(name = "seed")
    val seed: Long?,
    @ColumnInfo(name = "steps")
    val steps: Int,
    @ColumnInfo(name = "n_samples")
    val nSamples: Int,
    @ColumnInfo(name = "full_url")
    val fullUrl: String,
    @ColumnInfo(name = "upscale")
    val upscale: String,
    @ColumnInfo(name = "multi_lingual")
    val multiLingual: String,
    @ColumnInfo(name = "panorama")
    val panorama: String,
    @ColumnInfo(name = "self_attention")
    val selfAttention: String,
    @ColumnInfo(name = "embeddings")
    val embeddings: String?,
    @ColumnInfo(name = "lora")
    val lora: String?,
    @ColumnInfo(name = "outdir")
    val outdir: String?,
    @ColumnInfo(name = "file_prefix")
    val filePrefix: String?
)

fun TxtToImgResultMetaDataEntity.asDomainModel(): TxtToImgResultMetaData {
    return TxtToImgResultMetaData(
        prompt = prompt,
        modelId = modelId,
        negativePrompt = negativePrompt,
        scheduler = scheduler,
        safetychecker = safetychecker,
        w = w,
        h = h,
        guidanceScale = guidanceScale,
        seed = seed,
        steps = steps,
        nSamples = nSamples,
        fullUrl = fullUrl,
        upscale = upscale,
        multiLingual = multiLingual,
        panorama = panorama,
        selfAttention = selfAttention,
        embeddings = embeddings,
        lora = lora,
        outdir = outdir,
        filePrefix = filePrefix
    )
}

fun TxtToImgResultMetaData.asEntity(): TxtToImgResultMetaDataEntity {
    return TxtToImgResultMetaDataEntity(
        prompt = this.prompt,
        modelId = this.modelId,
        negativePrompt = this.negativePrompt,
        scheduler = scheduler,
        safetychecker = safetychecker,
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