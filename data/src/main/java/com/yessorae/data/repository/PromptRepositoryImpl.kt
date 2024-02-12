package com.yessorae.data.repository

import com.yessorae.data.local.database.dao.PromptDao
import com.yessorae.data.local.database.model.PromptEntity
import com.yessorae.data.local.database.model.asDomainModel
import com.yessorae.data.local.database.model.asEntityModel
import com.yessorae.domain.model.parameter.Prompt
import com.yessorae.domain.repository.PromptRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PromptRepositoryImpl @Inject constructor(
    private val promptDao: PromptDao,
    private val preferenceRepositoryImpl: PreferenceRepositoryImpl
) : PromptRepository {

    override fun getPositivePrompts(): Flow<List<Prompt>> {
        return promptDao.getPromptsOrderedByUpdatedAt(
            isPositive = true
        ).map {
            it.map(PromptEntity::asDomainModel)
        }
    }

    override fun getNegativePrompts(): Flow<List<Prompt>> {
        return promptDao.getPromptsOrderedByUpdatedAt(
            isPositive = false
        ).map {
            it.map(PromptEntity::asDomainModel)
        }
    }

    override suspend fun insertPrompt(prompt: Prompt): String {
        promptDao.insert(prompt.asEntityModel())
        return prompt.asEntityModel().prompt
    }

    override suspend fun insertPrompts(prompts: List<Prompt>) {
        promptDao.insertAll(prompts.map(Prompt::asEntityModel))
    }

    override suspend fun deletePrompt(prompt: Prompt) {
        promptDao.delete(entity = prompt.asEntityModel())
    }
}