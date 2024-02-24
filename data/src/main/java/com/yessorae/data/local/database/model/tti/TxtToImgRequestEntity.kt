package com.yessorae.data.local.database.model.tti

import androidx.room.ColumnInfo
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequestDto
import com.yessorae.domain.model.tti.TxtToImgRequest
import com.yessorae.domain.util.StableDiffusionConstants

data class TxtToImgRequestEntity(
    @ColumnInfo(name = "model_id")
    val modelId: String,
    @ColumnInfo(name = "prompt")
    val prompt: String,
    @ColumnInfo(name = "negative_prompt")
    val negativePrompt: String,
    @ColumnInfo(name = "width")
    val width: Int,
    @ColumnInfo(name = "height")
    val height: Int,
    @ColumnInfo(name = "samples")
    val samples: Int,
    @ColumnInfo(name = "num_inference_steps")
    val numInferenceSteps: Int,
    @ColumnInfo(name = "safety_checker")
    val safetyChecker: String,
    @ColumnInfo(name = "enhance_prompt")
    val enhancePrompt: String,
    @ColumnInfo(name = "seed")
    val seed: String?,
    @ColumnInfo(name = "guidance_scale")
    val guidanceScale: Double,
    @ColumnInfo(name = "lora_strength")
    val loraStrength: String?,
    @ColumnInfo(name = "lora_model")
    val loraModel: String?,
    @ColumnInfo(name = "multi_lingual")
    val multiLingual: String,
    @ColumnInfo(name = "upscale")
    val upscale: String,
    @ColumnInfo(name = "embeddings_model")
    val embeddingsModel: String?,
    @ColumnInfo(name = "scheduler")
    val scheduler: String,
    @ColumnInfo(name = "clip_skip")
    val clipSkip: Int = 2,
    @ColumnInfo(name = "safety_checker_type")
    val safetyCheckerType: String = "blur",
    @ColumnInfo(name = "tomesd")
    val tomesd: String = StableDiffusionConstants.ARG_YES,
    @ColumnInfo(name = "use_karras_sigmas")
    val useKarrasSigmas: String = StableDiffusionConstants.ARG_YES,
    @ColumnInfo(name = "algorithm_type")
    val algorithmType: String = "sde-dpmsolver++",
    @ColumnInfo(name = "vae")
    val vae: String? = null,
    @ColumnInfo(name = "panorama")
    val panorama: String = StableDiffusionConstants.ARG_NO,
    @ColumnInfo(name = "self_attention")
    val selfAttention: String = StableDiffusionConstants.ARG_YES,
    @ColumnInfo(name = "base64")
    val base64: String = StableDiffusionConstants.ARG_NO,
    @ColumnInfo(name = "webhook")
    val webhook: String? = null,
    @ColumnInfo(name = "track_id")
    val trackId: String? = null,
    @ColumnInfo(name = "temp")
    val temp: String = StableDiffusionConstants.ARG_YES
)

fun TxtToImgRequestEntity.asDomainModel(): TxtToImgRequest {
    return TxtToImgRequest(
        prompt = prompt,
        modelId = modelId,
        negativePrompt = negativePrompt,
        width = width,
        height = height,
        guidanceScale = guidanceScale,
        seed = seed?.toLongOrNull(),
        steps = numInferenceSteps,
        nSamples = samples,
        upscale = upscale,
        multiLingual = multiLingual,
        panorama = panorama,
        selfAttention = selfAttention,
        embeddings = embeddingsModel,
        lora = loraModel,
        loraStrength = loraStrength,
        scheduler = scheduler,
        safetyChecker = safetyChecker,
        enhancePrompt = enhancePrompt
    )
}

fun TxtToImgRequest.asEntity(): TxtToImgRequestEntity {
    return TxtToImgRequestEntity(
        prompt = prompt,
        modelId = modelId,
        negativePrompt = negativePrompt,
        width = width,
        height = height,
        guidanceScale = guidanceScale,
        seed = seed?.toString(),
        numInferenceSteps = steps,
        samples = nSamples,
        upscale = upscale,
        multiLingual = multiLingual,
        panorama = panorama,
        selfAttention = selfAttention,
        embeddingsModel = embeddings,
        loraModel = lora,
        loraStrength = loraStrength,
        scheduler = scheduler,
        safetyChecker = safetyChecker,
        enhancePrompt = enhancePrompt
    )
}
