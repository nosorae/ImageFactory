package com.yessorae.imagefactory.ui.screen.result.model

data class UpscaleResultModel(
    val id: Int,
    val outputUrl: String,
    val status: String,
    val generationTime: Double?
)
