package com.yessorae.imagefactory.model

import com.yessorae.imagefactory.ui.components.item.model.CoverOption
import com.yessorae.imagefactory.util.StringModel

data class LoRaModelOption(
    val strength: Float = DEFAULT_STRENGTH,
    override val id: String,
    override val image: Any,
    override val title: StringModel,
    override val selected: Boolean,
    val generationCount: Long?
) : CoverOption {
    companion object {
        const val DEFAULT_STRENGTH: Float = 0f
    }
}

fun List<LoRaModelOption>.toLoRaModelPrompt(): String {
    return this.filter { it.selected }.joinToString { it.id }
}

fun List<LoRaModelOption>.toLoRaStrengthPrompt(): String {
    return this.filter { it.selected }.joinToString { it.strength.toString() }
}
