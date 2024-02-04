package com.yessorae.data.remote.stablediffusion.model.response

import com.google.gson.annotations.SerializedName
import com.yessorae.data.util.replaceDomain
import com.yessorae.domain.model.TxtToImgResult

data class TxtToImgDto(
    @SerializedName("status") val status: String,
    @SerializedName("generationTime") val generationTime: Double,
    @SerializedName("id") val id: Int,
    @SerializedName("output") val output: List<String>,
    @SerializedName("meta") val meta: MetaDataResponseDto
)

fun TxtToImgDto.asDomainModel(): TxtToImgResult {
    return TxtToImgResult(
        id = id,
        outputUrls = output.map { it.replaceDomain() },
        status = status,
        generationTime = generationTime
    )
}

