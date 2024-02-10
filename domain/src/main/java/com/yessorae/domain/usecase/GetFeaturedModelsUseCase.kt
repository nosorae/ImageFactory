package com.yessorae.domain.usecase

import com.yessorae.domain.model.parameter.Model
import com.yessorae.domain.repository.PublicModelRepository
import javax.inject.Inject

class GetFeaturedModelsUseCase @Inject constructor(
    private val publicModelRepository: PublicModelRepository
) {
    suspend operator fun invoke(): List<Model> {
        val stableDiffusionModels = publicModelRepository.getFeaturedModelsOfStableDiffusion()
        val loRaModels = publicModelRepository.getFeaturedModelsOfLoRa()
        val embeddingsModels = publicModelRepository.getFeaturedModelsOfEmbeddings()
        return stableDiffusionModels + loRaModels + embeddingsModels
    }
}