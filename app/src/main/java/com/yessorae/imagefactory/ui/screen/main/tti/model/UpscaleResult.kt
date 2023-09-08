package com.yessorae.imagefactory.ui.screen.main.tti.model

data class UpscaleResult(
    val id: Int,
    val outputUrl: String,
    val status: String,
    val generationTime: Double?
)