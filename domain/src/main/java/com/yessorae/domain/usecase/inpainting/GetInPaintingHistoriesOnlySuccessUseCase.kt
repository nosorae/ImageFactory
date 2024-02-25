package com.yessorae.domain.usecase.inpainting

import com.yessorae.domain.model.inpainting.InPaintingHistory
import com.yessorae.domain.repository.inpainting.InPaintingHistoryRepository
import com.yessorae.domain.util.StableDiffusionConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetInPaintingHistoriesOnlySuccessUseCase @Inject constructor(
    private val inPaintingHistoryRepository: InPaintingHistoryRepository
) {
    operator fun invoke(): Flow<List<InPaintingHistory>> {
        return inPaintingHistoryRepository.getHistories()
            .map { list ->
                list.filter { history ->
                    history.result?.status == StableDiffusionConstants.RESPONSE_SUCCESS ||
                            history.result?.outputUrls.isNullOrEmpty().not()
                }
            }
    }
}