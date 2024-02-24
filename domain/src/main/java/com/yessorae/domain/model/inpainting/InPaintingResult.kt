package com.yessorae.domain.model.inpainting


data class InPaintingResult(
    val id: Int,
    val outputUrls: List<String>,
    val status: String,
    val generationTime: Double?,
    val metaData: InPaintingResultMetaData? = null
) {
    val firstImgUrl = outputUrls.firstOrNull()
}