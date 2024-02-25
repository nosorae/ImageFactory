package com.yessorae.domain.usecase.model

import com.yessorae.domain.model.parameter.LoRaModel
import com.yessorae.domain.repository.ModelRepository
import javax.inject.Inject

class InsertUsedLoRaModelUseCase @Inject constructor(
    private val modelRepository: ModelRepository
) {
    suspend operator fun invoke(model: LoRaModel): Long {
        return modelRepository.insertLoRaModel(model)
    }
}