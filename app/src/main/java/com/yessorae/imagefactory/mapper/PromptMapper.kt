package com.yessorae.imagefactory.mapper

import com.yessorae.data.local.database.model.PromptEntity
import com.yessorae.imagefactory.model.PromptOption
import com.yessorae.imagefactory.util.TextString
import java.time.LocalDateTime
import javax.inject.Inject

class PromptMapper @Inject constructor() {
    fun map(dto: List<PromptEntity>): List<PromptOption> {
        return dto.map {
            PromptOption(
                dbId = it.prompt,
                title = TextString(it.prompt),
                selected = false
            )
        }
    }

    fun mapToEntity(prompt: String, positive: Boolean): PromptEntity {
        return PromptEntity(
            prompt = prompt,
            positive = positive,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }
}
