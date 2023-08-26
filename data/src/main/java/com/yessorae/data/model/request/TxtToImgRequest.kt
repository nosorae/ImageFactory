package com.yessorae.data.model.request

import com.google.gson.annotations.SerializedName
import com.yessorae.data.BuildConfig

data class TxtToImgRequest(
    @SerializedName("key") val key: String = BuildConfig.STABLE_DIFFUSION_API_API_KEY,
    @SerializedName("model_id") val modelId: String,
    @SerializedName("prompt") val prompt: String,
    @SerializedName("negative_prompt") val negativePrompt: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("samples") val samples: Int,
    @SerializedName("num_inference_steps") val numInferenceSteps: Int,
    @SerializedName("safety_checker") val safetyChecker: String,
    @SerializedName("safety_checker_type") val safetyCheckerType: String,
    @SerializedName("enhance_prompt") val enhancePrompt: String,
    @SerializedName("seed") val seed: String?,
    @SerializedName("guidance_scale") val guidanceScale: Double,
    @SerializedName("tomesd") val tomesd: String,
    @SerializedName("use_karras_sigmas") val useKarrasSigmas: String,
    @SerializedName("algorithm_type") val algorithmType: String,
    @SerializedName("vae") val vae: String?,
    @SerializedName("lora_strength") val loraStrength: String?,
    @SerializedName("lora_model") val loraModel: String?,
    @SerializedName("multi_lingual") val multiLingual: String,
    @SerializedName("panorama") val panorama: String,
    @SerializedName("self_attention") val selfAttention: String,
    @SerializedName("upscale") val upscale: String,
    @SerializedName("clip_skip") val clipSkip: Int,
    @SerializedName("base64") val base64: String,
    @SerializedName("embeddings_model") val embeddingsModel: String?,
    @SerializedName("scheduler") val scheduler: String,
    @SerializedName("webhook") val webhook: String?,
    @SerializedName("track_id") val trackId: String?,
    @SerializedName("temp") val temp: String
)
