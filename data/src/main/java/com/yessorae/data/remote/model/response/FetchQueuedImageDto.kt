package com.yessorae.data.remote.model.response


import com.google.gson.annotations.SerializedName

data class FetchQueuedImageDto(
    @SerializedName("status")
    val status: String, // success
    @SerializedName("id")
    val id: Int, // 13443927
    @SerializedName("output")
    val output: List<String>
)