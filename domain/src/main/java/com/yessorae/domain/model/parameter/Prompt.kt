package com.yessorae.domain.model.parameter

data class Prompt(
    val prompt: String,
    val positive: Boolean,
    val nsfw: Boolean = false
) {
    companion object {
        fun mock(): List<Prompt> {
            val words = listOf(
                "Apple", "Banana", "Cherry", "Dog", "Elephant", "Fox", "Grapes",
                "Horse", "Igloo", "Jazz", "Kite", "Lion", "Monkey", "Nest", "Owl", "Penguin",
                "Quill", "Rabbit", "Snake", "Tiger"
            )

            return words.mapIndexed { index, word ->
                Prompt(
                    prompt = word,
                    positive = true,
                    nsfw = index % 3 == 0
                )
            }
        }

        fun create(prompt: String, positive: Boolean): Prompt {
            return Prompt(
                prompt = prompt,
                positive = positive
            )
        }
    }
}

fun Prompt.Companion.createPositive(prompt: String, nsfw: Boolean = false): Prompt {
    return Prompt(
        prompt = prompt,
        positive = true,
        nsfw = false
    )
}

fun Prompt.Companion.createNegative(prompt: String, nsfw: Boolean = false): Prompt {
    return Prompt(
        prompt = prompt,
        positive = false,
        nsfw = nsfw,
    )
}
