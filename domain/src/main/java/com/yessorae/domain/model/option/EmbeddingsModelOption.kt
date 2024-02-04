package com.yessorae.domain.model.option

data class EmbeddingsModelOption(
    override val id: String,
    override val image: Any,
    override val title: String,
    override val selected: Boolean,
    val generationCount: Long?
) : CoverOption {
    companion object
}
