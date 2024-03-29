package com.yessorae.domain.model

data class TxtToImgResult(
    val id: Int,
    val outputUrls: List<String>,
    val status: String,
    val generationTime: Double?,
    val metaData: TxtToImgResultMetaData? = null
) {
    val firstImgUrl = outputUrls.firstOrNull()
}
