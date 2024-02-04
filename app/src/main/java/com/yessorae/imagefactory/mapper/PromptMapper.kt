package com.yessorae.imagefactory.mapper

import com.yessorae.data.local.database.model.PromptEntity
import com.yessorae.domain.model.option.PromptOption
import com.yessorae.imagefactory.util.TextString
import java.time.LocalDateTime
import javax.inject.Inject

class PromptMapper @Inject constructor() {
    fun map(dto: List<PromptEntity>, lastPromptIds: List<String>? = null): List<PromptOption> {
        val lastPromptSet = lastPromptIds?.toSet()
        return dto.map {
            PromptOption(
                dbId = it.prompt,
                title = TextString(it.prompt),
                selected = lastPromptSet?.contains(element = it.prompt) ?: false
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
