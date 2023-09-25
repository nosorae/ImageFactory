package com.yessorae.imagefactory.model

import com.yessorae.common.Constants
import com.yessorae.imagefactory.ui.components.item.model.Option
import com.yessorae.imagefactory.util.StringModel
import com.yessorae.imagefactory.util.TextString
import com.yessorae.imagefactory.util.isMultiLanguage

data class PromptOption(
    val dbId: String,
    override val title: StringModel,
    override val selected: Boolean
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
        PromptOption(dbId = word, title = TextString(word), selected = index % words.size == 0)
    }
}

fun List<PromptOption>.isMultiLingual(): Boolean {
    return this.any { !it.title.get().isMultiLanguage() }
}

fun List<PromptOption>.toPrompt(): String {
    return this.filter { it.selected }.joinToString { it.title.get() }
}
