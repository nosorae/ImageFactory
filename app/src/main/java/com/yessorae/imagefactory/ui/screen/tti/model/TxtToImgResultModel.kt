package com.yessorae.imagefactory.ui.screen.tti.model

data class TxtToImgResultModel(
    val id: Int,
    val outputUrls: List<String>,
    val status: String,
    val generationTime: Double?
)
