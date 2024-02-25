package com.yessorae.domain.usecase.prompt

import com.yessorae.domain.model.parameter.Prompt
import com.yessorae.domain.repository.PromptRepository
import javax.inject.Inject

class InsertPromptUseCase @Inject constructor(
    private val promptRepository: PromptRepository
) {
    suspend operator fun invoke(prompt: Prompt) {
        promptRepository.insertPrompt(prompt = prompt)
    }
}