package com.yessorae.data.remote.stablediffusion.model.request

import com.google.gson.annotations.SerializedName
import com.yessorae.data.BuildConfig
import com.yessorae.data.local.database.model.inpainting.InPaintingRequestEntity
import com.yessorae.domain.model.inpainting.InPaintingRequest

data class InPaintingRequestDto(
    @SerializedName("key") val key: String = BuildConfig.STABLE_DIFFUSION_API_API_KEY,


    @SerializedName("model_id") val modelId: String,
    @SerializedName("prompt") val prompt: String,
    @SerializedName("negative_prompt") val negativePrompt: String,
    @SerializedName("init_image") val initImage: String,
    @SerializedName("mask_image") val maskImage: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("samples") val samples: Int,
    @SerializedName("steps") val steps: Int,

    @SerializedName("safety_checker") val safetyChecker: String,
    @SerializedName("safety_checker_type") val safetyCheckerType: String,
    @SerializedName("enhance_prompt") val enhancePrompt: String,

    @SerializedName("guidance_scale") val guidanceScale: Double,

    @SerializedName("tomesd") val tomesd: String,
    @SerializedName("use_karras_sigmas") val useKarrasSigmas: String,
    @SerializedName("algorithm_type") val algorithmType: String,
    @SerializedName("vae") val vae: String?,

    @SerializedName("lora_strength") val loraStrength: String?,
    @SerializedName("lora_model") val loraModel: String?,
    @SerializedName("strength") val strength: Double, // Prompt strength when using init image. 1.0 corresponds to full destruction of information in the init image.
    @SerializedName("scheduler") val scheduler: String,


    @SerializedName("seed") val seed: String?,
    @SerializedName("webhook") val webhook: String?,
    @SerializedName("track_id") val trackId: String?,
    @SerializedName("clip_skip") val clipSkip: Int,
    @SerializedName("base64") val base64: String,
    @SerializedName("temp") val temp: String,
    @SerializedName("embeddings_model") val embeddingsModel: String?
)

fun InPaintingRequest.asDto(
): InPaintingRequestDto {
    return InPaintingRequestDto(
        modelId = modelId,
        prompt = prompt,
        negativePrompt = negativePrompt,
        initImage = initImage,
        maskImage = maskImage,
        width = width,
        height = height,
        samples = samples ,
        steps = steps,
        safetyChecker = safetyChecker,
        // Modify image if NSFW images are found; default: sensitive_content_text, options: blur/sensitive_content_text/pixelate/black
        safetyCheckerType = safetyCheckerType,
        enhancePrompt = enhancePrompt,
        guidanceScale = guidanceScale,
        tomesd = tomesd,
        useKarrasSigmas = useKarrasSigmas,
        algorithmType = algorithmType,
        vae = vae,
        loraModel = loraModel,
        loraStrength = loraStrength,
        // Prompt strength when using init image. 1.0 corresponds to full destruction(파괴) of information in the init image.
        strength = strength,
        scheduler = scheduler,
        seed = seed,
        webhook = webhook,
        trackId = trackId,
        clipSkip = clipSkip,
        base64 = base64,
        temp = temp,
        embeddingsModel = embeddingsModel
    )
}

fun InPaintingRequestDto.asEntity(): InPaintingRequestEntity {
    return InPaintingRequestEntity(
        modelId = modelId,
        prompt = prompt,
        negativePrompt = negativePrompt,
        initImage = initImage,
        maskImage = maskImage,
        width = width,
        height = height,
        samples = samples ,
        steps = steps,
        safetyChecker = safetyChecker,
        // Modify image if NSFW images are found; default: sensitive_content_text, options: blur/sensitive_content_text/pixelate/black
        safetyCheckerType = safetyCheckerType,
        enhancePrompt = enhancePrompt,
        guidanceScale = guidanceScale,
        tomesd = tomesd,
        useKarrasSigmas = useKarrasSigmas,
        algorithmType = algorithmType,
        vae = vae,
        loraModel = loraModel,
        loraStrength = loraStrength,
        // Prompt strength when using init image. 1.0 corresponds to full destruction(파괴) of information in the init image.
        strength = strength,
        scheduler = scheduler,
        seed = seed,
        webhook = webhook,
        trackId = trackId,
        clipSkip = clipSkip,
        base64 = base64,
        temp = temp,
        embeddingsModel = embeddingsModel
    )
}