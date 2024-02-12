package com.yessorae.domain.usecase

import com.yessorae.domain.model.parameter.LoRaModel
import com.yessorae.domain.model.parameter.Model
import com.yessorae.domain.repository.ModelRepository
import com.yessorae.domain.util.StableDiffusionConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toSet
import javax.inject.Inject

class GetFeaturedLoRaModelsUseCase @Inject constructor(
    private val modelRepository: ModelRepository
) {
    operator fun invoke(): Flow<List<LoRaModel>> {
        return modelRepository.getRecentlyUsedLoRaModels().map { recentlyUsedModel ->
            val featuredModels = modelRepository.getFeaturedLoRaModels()
            (recentlyUsedModel + featuredModels)
                .toSet()
                .toList()
                .take(StableDiffusionConstants.LIMIT_FEATURED_MODEL_LIST_COUNT)
        }
    }
}
