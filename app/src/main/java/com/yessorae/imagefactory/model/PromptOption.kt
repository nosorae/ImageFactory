package com.yessorae.imagefactory.model

import com.yessorae.imagefactory.ui.item.model.Option
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString

data class PromptOption(
    override val text: StringModel,
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
        PromptOption(text = TextString(word), selected = index % words.size == 0)
    }
}


