package com.yessorae.imagefactory.model

import com.yessorae.imagefactory.ui.component.model.Chip
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString

data class ChipModel(
    override val text: StringModel,
    override val selected: Boolean
) : Chip {
    companion object
}


fun ChipModel.Companion.mock(): List<ChipModel> {
    val words = listOf(
        "Apple", "Banana", "Cherry", "Dog", "Elephant", "Fox", "Grapes",
        "Horse", "Igloo", "Jazz", "Kite", "Lion", "Monkey", "Nest", "Owl", "Penguin",
        "Quill", "Rabbit", "Snake", "Tiger"
    )

    return words.mapIndexed { index, word ->
        ChipModel(text = TextString(word), selected = index % words.size == 0)
    }
}


