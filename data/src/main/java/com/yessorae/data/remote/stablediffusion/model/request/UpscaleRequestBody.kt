package com.yessorae.data.remote.stablediffusion.model.request


import com.google.gson.annotations.SerializedName

data class UpscaleRequestBody(
    @SerializedName("key")
    val key: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("scale")
    val scale: Int, // 3
    @SerializedName("webhook")
    val webhook: Any? = null,
    @SerializedName("face_enhance")
    val faceEnhance: Boolean // false
)