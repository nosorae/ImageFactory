package com.yessorae.data.remote.stablediffusion.model.request

import com.google.gson.annotations.SerializedName
import com.yessorae.data.BuildConfig

data class KeyRequestDto(
    @SerializedName("key")
    val key: String = BuildConfig.STABLE_DIFFUSION_API_API_KEY
)
