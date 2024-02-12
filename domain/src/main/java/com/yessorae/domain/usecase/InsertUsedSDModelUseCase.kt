package com.yessorae.domain.usecase

import com.yessorae.domain.model.parameter.SDModel
import com.yessorae.domain.repository.ModelRepository
import javax.inject.Inject

class InsertUsedSDModelUseCase @Inject constructor(
    private val modelRepository: ModelRepository
) {
    suspend operator fun invoke(model: SDModel): Long {
        return modelRepository.insertSDModel(model)
    }
}
