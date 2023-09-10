package com.yessorae.imagefactory.ui.screen.result.model

data class TxtToImgResultModel(
    val id: Int,
    val outputUrls: List<String>,
    val status: String,
    val generationTime: Double?
) {
    val imageUrl: String? = outputUrls.firstOrNull()
}
