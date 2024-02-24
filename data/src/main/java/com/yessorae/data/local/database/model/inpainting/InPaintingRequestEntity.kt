package com.yessorae.data.local.database.model.inpainting

import androidx.room.ColumnInfo
import com.yessorae.domain.model.inpainting.InPaintingRequest

data class InPaintingRequestEntity(
    @ColumnInfo("model_id")
    val modelId: String,
    @ColumnInfo("prompt")
    val prompt: String,
    @ColumnInfo("negative_prompt")
    val negativePrompt: String,
    @ColumnInfo("init_image")
    val initImage: String,
    @ColumnInfo("mask_image")
    val maskImage: String,
    @ColumnInfo("width")
    val width: Int,
    @ColumnInfo("height")
    val height: Int,
    @ColumnInfo("samples")
    val samples: Int,
    @ColumnInfo("steps")
    val steps: Int,

    @ColumnInfo("safety_checker")
    val safetyChecker: String,
    @ColumnInfo("safety_checker_type")
    val safetyCheckerType: String,
    @ColumnInfo("enhance_prompt")
    val enhancePrompt: String,

    @ColumnInfo("guidance_scale")
    val guidanceScale: Double,

    @ColumnInfo("tomesd")
    val tomesd: String,
    @ColumnInfo("use_karras_sigmas")
    val useKarrasSigmas: String,
    @ColumnInfo("algorithm_type")
    val algorithmType: String,
    @ColumnInfo("vae")
    val vae: String?,

    @ColumnInfo("lora_strength")
    val loraStrength: String?,
    @ColumnInfo("lora_model")
    val loraModel: String?,
    @ColumnInfo("strength")
    val strength: Double, // Prompt strength when using init image. 1.0 corresponds to full destruction of information in the init image.
    @ColumnInfo("scheduler")
    val scheduler: String,


    @ColumnInfo("seed")
    val seed: String?,
    @ColumnInfo("webhook")
    val webhook: String?,
    @ColumnInfo("track_id")
    val trackId: String?,
    @ColumnInfo("clip_skip")
    val clipSkip: Int,
    @ColumnInfo("base64")
    val base64: String,
    @ColumnInfo("temp")
    val temp: String,
    @ColumnInfo("embeddings_model")
    val embeddingsModel: String?
)

fun InPaintingRequestEntity.asDomainModel(): InPaintingRequest {
    return InPaintingRequest(
        modelId = modelId,
        prompt = prompt,
        negativePrompt = negativePrompt,
        initImage = initImage,
        maskImage = maskImage,
        width = width,
        height = height,
        samples = samples,
        steps = steps,
        safetyChecker = safetyChecker,
        safetyCheckerType = safetyCheckerType,
        enhancePrompt = enhancePrompt,
        guidanceScale = guidanceScale,
        tomesd = tomesd,
        useKarrasSigmas = useKarrasSigmas,
        algorithmType = algorithmType,
        vae = vae,
        loraModel = loraModel,
        loraStrength = loraStrength,
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

fun InPaintingRequest.asEntity(): InPaintingRequestEntity {
    return InPaintingRequestEntity(
        modelId = modelId,
        prompt = prompt,
        negativePrompt = negativePrompt,
        initImage = initImage,
        maskImage = maskImage,
        width = width,
        height = height,
        samples = samples,
        steps = steps,
        safetyChecker = safetyChecker,
        safetyCheckerType = safetyCheckerType,
        enhancePrompt = enhancePrompt,
        guidanceScale = guidanceScale,
        tomesd = tomesd,
        useKarrasSigmas = useKarrasSigmas,
        algorithmType = algorithmType,
        vae = vae,
        loraModel = loraModel,
        loraStrength = loraStrength,
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
