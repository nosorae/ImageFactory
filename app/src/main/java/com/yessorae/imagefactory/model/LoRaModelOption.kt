package com.yessorae.imagefactory.model

import com.yessorae.imagefactory.ui.components.item.model.CoverOption
import com.yessorae.imagefactory.ui.util.StringModel

data class LoRaModelOption(
    val id: String,
    val strength: Float = 0f,
    override val image: Any,
    override val title: StringModel,
    override val selected: Boolean
) : CoverOption