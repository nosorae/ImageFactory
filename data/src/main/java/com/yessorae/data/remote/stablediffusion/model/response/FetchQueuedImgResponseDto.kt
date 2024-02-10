package com.yessorae.data.remote.stablediffusion.model.response

import com.google.gson.annotations.SerializedName
import com.yessorae.domain.model.FetchQueuedImgResponse

data class FetchQueuedImgResponseDto(
    @SerializedName("status")
    val status: String, // success
    @SerializedName("id")
    val id: Int, // 13443927
    @SerializedName("output")
    val output: List<String>
)

fun FetchQueuedImgResponseDto.asDomainModel(): FetchQueuedImgResponse {
    return FetchQueuedImgResponse(
        status = status,
        id = id,
        output = output
    )
}