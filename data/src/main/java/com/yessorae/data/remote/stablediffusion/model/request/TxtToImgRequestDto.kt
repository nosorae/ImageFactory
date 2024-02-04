package com.yessorae.data.remote.stablediffusion.model.request

import com.google.gson.annotations.SerializedName
import com.yessorae.domain.util.Constants
import com.yessorae.data.util.replaceDomain
import com.yessorae.data.BuildConfig
import com.yessorae.data.local.database.model.TxtToImgHistoryEntity
import com.yessorae.data.local.database.model.TxtToImgRequestEntity
import com.yessorae.data.local.database.model.asEntity
import com.yessorae.domain.model.TxtToImgRequest
import com.yessorae.domain.model.TxtToImgResult
import java.time.LocalDateTime

data class TxtToImgRequestDto(
    @SerializedName("key") val key: String = BuildConfig.STABLE_DIFFUSION_API_API_KEY,
    @SerializedName("model_id") val modelId: String,
    @SerializedName("prompt") val prompt: String,
    @SerializedName("negative_prompt") val negativePrompt: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("samples") val samples: Int,
    @SerializedName("num_inference_steps") val numInferenceSteps: Int,
    @SerializedName("safety_checker") val safetyChecker: String,
    @SerializedName("enhance_prompt") val enhancePrompt: String,
    @SerializedName("seed") val seed: String?,
    @SerializedName("guidance_scale") val guidanceScale: Double,
    @SerializedName("lora_strength") val loraStrength: String?,
    @SerializedName("lora_model") val loraModel: String?,
    @SerializedName("multi_lingual") val multiLingual: String,
    @SerializedName("upscale") val upscale: String,
    @SerializedName("embeddings_model") val embeddingsModel: String?,
    @SerializedName("scheduler") val scheduler: String,
    @SerializedName("clip_skip") val clipSkip: Int = 2,
    @SerializedName("safety_checker_type") val safetyCheckerType: String = "blur",
    @SerializedName("tomesd") val tomesd: String = Constants.ARG_YES,
    @SerializedName("use_karras_sigmas") val useKarrasSigmas: String = Constants.ARG_YES,
    @SerializedName("algorithm_type") val algorithmType: String = "sde-dpmsolver++",
    @SerializedName("vae") val vae: String? = null,
    @SerializedName("panorama") val panorama: String = Constants.ARG_NO,
    @SerializedName("self_attention") val selfAttention: String = Constants.ARG_YES,
    @SerializedName("base64") val base64: String = Constants.ARG_NO,
    @SerializedName("webhook") val webhook: String? = null,
    @SerializedName("track_id") val trackId: String? = null,
    @SerializedName("temp") val temp: String = Constants.ARG_YES
)

fun TxtToImgRequestDto.asEntity(): TxtToImgRequestEntity {
    return TxtToImgRequestEntity(
        modelId = this.modelId,
        prompt = this.prompt,
        negativePrompt = this.negativePrompt,
        width = this.width,
        height = this.height,
        samples = this.samples,
        numInferenceSteps = this.numInferenceSteps,
        safetyChecker = this.safetyChecker,
        enhancePrompt = this.enhancePrompt,
        seed = this.seed,
        guidanceScale = this.guidanceScale,
        loraStrength = this.loraStrength,
        loraModel = this.loraModel,
        multiLingual = this.multiLingual,
        upscale = this.upscale,
        embeddingsModel = this.embeddingsModel,
        scheduler = this.scheduler
    )
}

fun TxtToImgRequestDto.asRequestDomainModel(): TxtToImgRequest {
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


fun TxtToImgRequestDto.asResultDomainModel(
    id: Int,
    outputUrls: List<String>,
    status: String,
    generationTime: Double? = null
): TxtToImgResult {
    return TxtToImgResult(
        id = id,
        outputUrls = outputUrls.map { it.replaceDomain() },
        status = status,
        generationTime = generationTime
    )
}

fun TxtToImgRequestDto.asHistoryEntity(): TxtToImgHistoryEntity {
    return TxtToImgHistoryEntity(
        createdAt = LocalDateTime.now(),
        request = this.asEntity()
    )
}

fun TxtToImgRequest.asRequestDto(): TxtToImgRequestDto {
    return TxtToImgRequestDto(
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
