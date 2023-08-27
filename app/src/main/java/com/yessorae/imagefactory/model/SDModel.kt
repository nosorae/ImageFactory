package com.yessorae.imagefactory.model

import com.yessorae.imagefactory.ui.component.model.Cover
import com.yessorae.imagefactory.ui.util.MockData
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString

data class SDModel(
    override val model: Any,
    override val title: StringModel,
    override val selected: Boolean
) : Cover {
    companion object
}

fun SDModel.Companion.mock(): List<Cover> {
    val words = listOf(
        "Apple", "Banana", "Cherry", "Dog", "Elephant", "Fox", "Grapes",
        "Horse", "Igloo", "Jazz", "Kite", "Lion", "Monkey", "Nest", "Owl", "Penguin",
        "Quill", "Rabbit", "Snake", "Tiger"
    )

    return words.mapIndexed { index, word ->
        SDModel(
            model = MockData.MOCK_IMAGE_URL,
            title = TextString(word),
            selected = index == 0
        )
    }
}