package com.yessorae.domain.model.option

import com.yessorae.domain.util.MockData

data class SDModelOption(
    override val id: String,
    override val image: Any,
    override val title: String,
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
            title = word,
            selected = index == 0,
            generationCount = index * 1000L
        )
    }
}
