package com.yessorae.data.local.preference.model

data class LastRequestOption(
    val positivePrompts: List<String>,
    val negativePrompts: List<String>,
    val sdModelOption: List<String>,
    val enhancePrompt: Boolean,
    val loRaModelsOptions: List<String>,
    val embeddingsModelOption: List<String>,
    val stepCount: Int,
    val safetyChecker: Boolean,
    val sizeOption: List<SDSizeType>,
    val seed: Long? = null,
    // Scale for classifier-free guidance (minimum: 1; maximum: 20)
    val guidanceScale: Int,
    val upscaleOption: List<UpscaleType>,
    val samples: Int,
    val schedulerOption: List<String>
)

enum class SDSizeType {
    Square,
    Portrait,
    Landscape
}

enum class UpscaleType {
    None,
    Twice,
    Triple
}