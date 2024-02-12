package com.yessorae.domain.usecase

import com.yessorae.domain.model.asEmbeddingsModels
import com.yessorae.domain.model.parameter.EmbeddingsModel
import com.yessorae.domain.repository.ModelRepository
import javax.inject.Inject

class GetAllEmbeddingsModelsUseCase @Inject constructor(
    private val modelRepository: ModelRepository
)  {
    suspend operator fun invoke(): List<EmbeddingsModel> {
        return modelRepository.getSyncedAllPublicModels().asEmbeddingsModels()
    }
}