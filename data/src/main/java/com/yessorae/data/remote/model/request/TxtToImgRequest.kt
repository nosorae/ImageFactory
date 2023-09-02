package com.yessorae.data.remote.model.request

import com.google.gson.annotations.SerializedName
import com.yessorae.common.Constants
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
    @SerializedName("enhance_prompt") val enhancePrompt: String,
    @SerializedName("seed") val seed: String?,
    @SerializedName("guidance_scale") val guidanceScale: Double,
    @SerializedName("lora_strength") val loraStrength: String?,
    @SerializedName("lora_model") val loraModel: String?,
    @SerializedName("multi_lingual") val multiLingual: String,
    @SerializedName("upscale") val upscale: String,
    @SerializedName("clip_skip") val clipSkip: Int = 2,
    @SerializedName("embeddings_model") val embeddingsModel: String?,
    @SerializedName("scheduler") val scheduler: String,
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
