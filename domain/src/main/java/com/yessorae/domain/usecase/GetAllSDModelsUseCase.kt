package com.yessorae.domain.usecase

import com.yessorae.domain.model.asSDModels
import com.yessorae.domain.model.parameter.SDModel
import com.yessorae.domain.repository.ModelRepository
import javax.inject.Inject

class GetAllSDModelsUseCase @Inject constructor(
    private val modelRepository: ModelRepository
) {
    suspend operator fun invoke(): List<SDModel> {
        return modelRepository.getSyncedAllPublicModels().asSDModels()
    }
}