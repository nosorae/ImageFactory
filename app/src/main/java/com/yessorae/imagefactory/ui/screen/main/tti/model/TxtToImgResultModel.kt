package com.yessorae.imagefactory.ui.screen.main.tti.model

data class TxtToImgResultModel(
    val id: Int,
    val outputUrls: List<String>,
    val status: String,
    val generationTime: Double?
)
