package com.yessorae.domain.model

data class UpscaleResult(
    val id: Int,
    val outputUrl: String,
    val status: String,
    val generationTime: Double?
)
