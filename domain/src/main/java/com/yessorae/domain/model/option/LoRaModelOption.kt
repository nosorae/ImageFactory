package com.yessorae.domain.model.option

data class LoRaModelOption(
    val strength: Float = DEFAULT_STRENGTH,
    override val id: String,
    override val image: Any,
    override val title: String,
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
