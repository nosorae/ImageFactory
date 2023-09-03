package com.yessorae.imagefactory.model

import com.yessorae.imagefactory.ui.components.item.model.CoverOption
import com.yessorae.imagefactory.ui.util.MockData
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString

data class SDModelOption(
    override val id: String,
    override val image: Any,
    override val title: StringModel,
    override val selected: Boolean,
    val generationCount: Long?,
    val isSdxl: Boolean = false
) : CoverOption {
    companion object
}

fun SDModelOption.Companion.mock(): List<CoverOption> {
    val words = listOf(
        "Apple", "Banana", "Cherry", "Dog", "Elephant", "Fox", "Grapes",
        "Horse", "Igloo", "Jazz", "Kite", "Lion", "Monkey", "Nest", "Owl", "Penguin",
        "Quill", "Rabbit", "Snake", "Tiger"
    )

    return words.mapIndexed { index, word ->
        SDModelOption(
            id = index.toString(),
            image = MockData.MOCK_IMAGE_URL,
            title = TextString(word),
            selected = index == 0,
            generationCount = index * 1000L
        )
    }
}
