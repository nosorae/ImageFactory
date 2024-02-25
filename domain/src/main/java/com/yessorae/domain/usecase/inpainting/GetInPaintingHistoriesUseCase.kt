package com.yessorae.domain.usecase.inpainting

import com.yessorae.domain.model.inpainting.InPaintingHistory
import com.yessorae.domain.repository.inpainting.InPaintingHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInPaintingHistoriesUseCase @Inject constructor(
    private val inPaintingHistoryRepository: InPaintingHistoryRepository
) {
    operator fun invoke(): Flow<List<InPaintingHistory>> {
        // TODO:: Paging 구현
        return inPaintingHistoryRepository.getHistories()
    }
}