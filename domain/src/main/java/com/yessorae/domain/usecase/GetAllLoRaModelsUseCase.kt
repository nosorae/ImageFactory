package com.yessorae.domain.usecase

import com.yessorae.domain.model.asLoRaModels
import com.yessorae.domain.model.parameter.LoRaModel
import com.yessorae.domain.repository.ModelRepository
import javax.inject.Inject

class GetAllLoRaModelsUseCase @Inject constructor(
    private val modelRepository: ModelRepository
) {
    suspend operator fun invoke(): List<LoRaModel> {
        return modelRepository.getSyncedAllPublicModels().asLoRaModels()
    }
}