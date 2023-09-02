package com.yessorae.imagefactory.mapper

import com.yessorae.data.local.model.PromptEntity
import com.yessorae.imagefactory.model.PromptOption
import com.yessorae.imagefactory.ui.util.TextString
import javax.inject.Inject

class PromptMapper @Inject constructor() {
    fun map(dto: List<PromptEntity>): List<PromptOption> {
        return dto.map {
            PromptOption(
                dbId = it.promptId,
                title = TextString(it.prompt),
                selected = false
            )
        }
    }
}