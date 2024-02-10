package com.yessorae.domain.repository

import com.yessorae.domain.model.parameter.Prompt
import kotlinx.coroutines.flow.Flow

interface PromptRepository {
    fun getPositivePrompts(): Flow<List<Prompt>>

    fun getNegativePrompts(): Flow<List<Prompt>>

    suspend fun insertPrompt(prompt: Prompt): String

    suspend fun insertPrompts(prompts: List<Prompt>)
}