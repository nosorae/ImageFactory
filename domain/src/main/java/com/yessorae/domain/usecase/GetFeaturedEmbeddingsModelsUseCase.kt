package com.yessorae.domain.usecase

import com.yessorae.domain.model.parameter.EmbeddingsModel
import com.yessorae.domain.model.parameter.Model
import com.yessorae.domain.repository.ModelRepository
import com.yessorae.domain.util.StableDiffusionConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toSet
import javax.inject.Inject

// TODO:: SR-N Featured 를 Preview 로 변경 고민중
class GetFeaturedEmbeddingsModelsUseCase @Inject constructor(
    private val modelRepository: ModelRepository
) {
    operator fun invoke(): Flow<List<EmbeddingsModel>> {
        return modelRepository.getRecentlyUsedEmbeddingsModels().map { recentlyUsedModel ->
            val featuredModels = modelRepository.getFeaturedEmbeddingsModels()
            (recentlyUsedModel + featuredModels)
                .toSet()
                .toList()
                .take(StableDiffusionConstants.LIMIT_FEATURED_MODEL_LIST_COUNT)
        }
    }
}