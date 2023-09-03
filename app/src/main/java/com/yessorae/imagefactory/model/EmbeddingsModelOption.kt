package com.yessorae.imagefactory.model

import com.yessorae.imagefactory.ui.components.item.model.CoverOption
import com.yessorae.imagefactory.ui.util.StringModel

data class EmbeddingsModelOption(
    override val id: String,
    override val image: Any,
    override val title: StringModel,
    override val selected: Boolean,
    val generationCount: Long?
) : CoverOption {
    companion object
}
