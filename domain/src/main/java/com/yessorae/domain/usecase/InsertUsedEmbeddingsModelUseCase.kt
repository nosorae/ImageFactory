package com.yessorae.domain.usecase

import com.yessorae.domain.model.parameter.EmbeddingsModel
import com.yessorae.domain.repository.ModelRepository
import javax.inject.Inject

class InsertUsedEmbeddingsModelUseCase @Inject constructor(
    private val modelRepository: ModelRepository
) {
    suspend operator fun invoke(model: EmbeddingsModel): Long {
        return modelRepository.insertEmbeddingsModel(model)
    }
}