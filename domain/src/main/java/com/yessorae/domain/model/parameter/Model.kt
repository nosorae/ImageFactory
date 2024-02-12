package com.yessorae.domain.model.parameter

import com.yessorae.domain.util.MockData
import com.yessorae.domain.util.StableDiffusionConstants.DEFAULT_LORA_STRENGTH

sealed class Model(
    open val id: String,
    open val imgUrl: String?,
    open val displayName: String
)

data class SDModel(
    override val id: String,
    override val imgUrl: String?,
    override val displayName: String,
): Model(id = id, imgUrl = imgUrl, displayName = displayName)  {
    companion object {
        fun mock(): List<SDModel> {
            val words = listOf(
                "Apple", "Banana", "Cherry", "Dog", "Elephant", "Fox", "Grapes",
                "Horse", "Igloo", "Jazz", "Kite", "Lion", "Monkey", "Nest", "Owl", "Penguin",
                "Quill", "Rabbit", "Snake", "Tiger"
            )

            return words.mapIndexed { index, word ->
                SDModel(
                    id = index.toString(),
                    imgUrl = MockData.MOCK_IMAGE_URL,
                    displayName = word
                )
            }
        }
    }
}

data class LoRaModel(
    override val id: String,
    override val imgUrl: String?,
    override val displayName: String,
    val strength: Float = DEFAULT_LORA_STRENGTH
): Model(id = id, imgUrl = imgUrl, displayName = displayName)

data class EmbeddingsModel(
    override val id: String,
    override val imgUrl: String?,
    override val displayName: String
): Model(id = id, imgUrl = imgUrl, displayName = displayName)