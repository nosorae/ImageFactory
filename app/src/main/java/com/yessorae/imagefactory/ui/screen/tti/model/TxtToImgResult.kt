package com.yessorae.imagefactory.ui.screen.tti.model

import com.yessorae.imagefactory.model.PromptOption

data class TxtToImgResult(
    val id: Int,
    val generationTime: Double,
    val outputUrl: List<String>,
    val prompt: List<PromptOption>,
    val negativePrompt: List<PromptOption>
)
