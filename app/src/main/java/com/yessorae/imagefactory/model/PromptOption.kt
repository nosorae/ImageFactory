package com.yessorae.imagefactory.model

import com.yessorae.imagefactory.ui.components.item.model.Option
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString
import com.yessorae.imagefactory.ui.util.isMultiLanguage

data class PromptOption(
    val id: Int,
    override val title: StringModel, // todo string으로 변경?
    override val selected: Boolean
) : Option {
    companion object
}


fun PromptOption.Companion.mock(): List<PromptOption> {
    val words = listOf(
        "Apple", "Banana", "Cherry", "Dog", "Elephant", "Fox", "Grapes",
        "Horse", "Igloo", "Jazz", "Kite", "Lion", "Monkey", "Nest", "Owl", "Penguin",
        "Quill", "Rabbit", "Snake", "Tiger"
    )

    return words.mapIndexed { index, word ->
        PromptOption(id = index, title = TextString(word), selected = index % words.size == 0)
    }
}

fun List<PromptOption>.isMultiLingual(): Boolean {
    return this.any { !it.title.get().isMultiLanguage() }
}

fun List<PromptOption>.toPrompt(): String {
    return this.joinToString { it.title.get() }
}
