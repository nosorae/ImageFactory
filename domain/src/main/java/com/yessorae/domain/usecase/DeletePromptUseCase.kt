package com.yessorae.domain.usecase

import com.yessorae.domain.model.parameter.Prompt
import com.yessorae.domain.repository.PromptRepository
import javax.inject.Inject

class DeletePromptUseCase @Inject constructor(
    private val promptRepository: PromptRepository
) {
    suspend operator fun invoke(prompt: Prompt) {
        promptRepository.deletePrompt(prompt = prompt)
    }
}