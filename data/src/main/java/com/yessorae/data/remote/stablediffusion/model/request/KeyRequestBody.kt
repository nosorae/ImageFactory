package com.yessorae.data.remote.stablediffusion.model.request

import com.google.gson.annotations.SerializedName

data class KeyRequestBody(
    @SerializedName("key")
    val key: String
)
