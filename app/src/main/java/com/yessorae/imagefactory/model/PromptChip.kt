package com.yessorae.imagefactory.model

import com.yessorae.imagefactory.ui.item.model.Chip
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString

data class PromptChip(
    override val text: StringModel,
    override val selected: Boolean
) : Chip {
    companion object
}


fun PromptChip.Companion.mock(): List<PromptChip> {
    val words = listOf(
        "Apple", "Banana", "Cherry", "Dog", "Elephant", "Fox", "Grapes",
        "Horse", "Igloo", "Jazz", "Kite", "Lion", "Monkey", "Nest", "Owl", "Penguin",
        "Quill", "Rabbit", "Snake", "Tiger"
    )

    return words.mapIndexed { index, word ->
        PromptChip(text = TextString(word), selected = index % words.size == 0)
    }
}


