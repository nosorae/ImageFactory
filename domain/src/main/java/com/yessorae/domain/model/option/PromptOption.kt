package com.yessorae.domain.model.option

import com.yessorae.domain.util.isMultiLanguage

data class PromptOption(
    val dbId: String,
    val isPositive: Boolean,
    override val title: String,
    override val selected: Boolean = false
) : Option {
    override val id: String = dbId

    companion object
}

fun PromptOption.Companion.mock(): List<PromptOption> {
    val words = listOf(
        "Apple", "Banana", "Cherry", "Dog", "Elephant", "Fox", "Grapes",
        "Horse", "Igloo", "Jazz", "Kite", "Lion", "Monkey", "Nest", "Owl", "Penguin",
        "Quill", "Rabbit", "Snake", "Tiger"
    )

    return words.mapIndexed { index, word ->
        PromptOption(
            dbId = word,
            title = word,
            isPositive = true,
            selected = index % words.size == 0
        )
    }
}

fun List<PromptOption>.isMultiLingual(): Boolean {
    return this.any { !it.title.isMultiLanguage() }
}

fun List<PromptOption>.toPrompt(): String {
    return this.filter { it.selected }.joinToString { it.title }
}
