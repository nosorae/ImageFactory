package com.yessorae.domain.repository

import com.yessorae.domain.model.option.PromptOption

interface PromptRepository {
    suspend fun processInitialPromptData()
    suspend fun getPositivePrompts(): List<PromptOption>

    suspend fun getNegativePrompts(): List<PromptOption>

    suspend fun insertPrompt(prompt: PromptOption): String
}