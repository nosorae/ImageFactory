package com.yessorae.imagefactory.ui.model

data class UpscaleResultModel(
    val id: Int,
    val outputUrl: String,
    val status: String,
    val generationTime: Double?
)
