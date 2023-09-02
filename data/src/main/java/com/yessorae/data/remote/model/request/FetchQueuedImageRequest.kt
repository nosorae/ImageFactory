package com.yessorae.data.remote.model.request


import com.google.gson.annotations.SerializedName
import com.yessorae.data.BuildConfig

data class FetchQueuedImageRequest(
    @SerializedName("key")
    val key: String = BuildConfig.STABLE_DIFFUSION_API_API_KEY,
    @SerializedName("request_id")
    val requestId: String // your_request_id
)