package com.yessorae.domain.usecase

import com.yessorae.domain.model.parameter.Prompt
import com.yessorae.domain.repository.PromptRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNegativePromptsFlowUseCase @Inject constructor(
    private val promptRepository: PromptRepository
) {
    operator fun invoke(): Flow<List<Prompt>> {
        return promptRepository.getNegativePrompts()
    }
}