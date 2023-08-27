package com.yessorae.imagefactory.model

import com.yessorae.imagefactory.ui.component.PromptChip
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString

data class PromptChipModel(
    override val text: StringModel,
    override val selected: Boolean
) : PromptChip {
    companion object
}


fun PromptChipModel.Companion.mock(): List<PromptChipModel> {
    val words = listOf(
        "Apple", "Banana", "Cherry", "Dog", "Elephant", "Fox", "Grapes",
        "Horse", "Igloo", "Jazz", "Kite", "Lion", "Monkey", "Nest", "Owl", "Penguin",
        "Quill", "Rabbit", "Snake", "Tiger"
    )

    return words.mapIndexed { index, word ->
        PromptChipModel(text = TextString(word), selected = index % words.size == 0)
    }
}


