package com.yessorae.data.model.request

import com.google.gson.annotations.SerializedName

data class KeyRequest(
    @SerializedName("key")
    val key: String
)
