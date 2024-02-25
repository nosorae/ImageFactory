package com.yessorae.domain.usecase.model

import com.yessorae.domain.model.parameter.SDModel
import com.yessorae.domain.repository.ModelRepository
import com.yessorae.domain.util.StableDiffusionConstants.LIMIT_FEATURED_MODEL_LIST_COUNT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFeaturedSDModelsUseCase @Inject constructor(
    private val modelRepository: ModelRepository
) {
    operator fun invoke(): Flow<List<SDModel>> {
        return modelRepository.getRecentlyUsedSDModels().map { recentlyUsedModel ->
            val featuredModels = modelRepository.getFeaturedSDModels()
            (recentlyUsedModel + featuredModels)
                .toSet()
                .toList()
                .take(LIMIT_FEATURED_MODEL_LIST_COUNT)
        }
    }
}
