package com.yessorae.data.local.database.model

import androidx.room.ColumnInfo
import com.yessorae.data.remote.stablediffusion.model.response.TxtToImgMetaDataResponseDto
import com.yessorae.domain.model.TxtToImgResultMetaData

data class ResultMetaDataEntity(
    @ColumnInfo(name = "prompt")
    val prompt: String,
    @ColumnInfo(name = "model_id")
    val modelId: String,
    @ColumnInfo(name = "negative_prompt")
    val negativePrompt: String,
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

fun ResultMetaDataEntity.asDomainModel(): TxtToImgResultMetaData {
    return TxtToImgResultMetaData(
        prompt = prompt,
        modelId = modelId,
        negativePrompt = negativePrompt,
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

fun TxtToImgResultMetaData.asEntity(): ResultMetaDataEntity {
    return ResultMetaDataEntity(
        prompt = prompt,
        modelId = modelId,
        negativePrompt = negativePrompt,
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

fun ResultMetaDataEntity.asDto(): TxtToImgMetaDataResponseDto {
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
