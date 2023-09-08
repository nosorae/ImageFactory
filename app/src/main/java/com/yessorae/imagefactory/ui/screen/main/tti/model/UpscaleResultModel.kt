package com.yessorae.imagefactory.ui.screen.main.tti.model

data class UpscaleResultModel(
    val id: Int,
    val beforeUrl: String,
    val outputUrl: String,
    val status: String,
    val generationTime: Double?
)