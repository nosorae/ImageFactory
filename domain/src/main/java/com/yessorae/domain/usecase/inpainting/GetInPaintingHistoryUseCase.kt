package com.yessorae.domain.usecase.inpainting

import com.yessorae.domain.model.inpainting.InPaintingHistory
import com.yessorae.domain.repository.inpainting.InPaintingHistoryRepository
import javax.inject.Inject

class GetInPaintingHistoryUseCase @Inject constructor(
    private val inPaintingHistoryRepository: InPaintingHistoryRepository
) {
    suspend operator fun invoke(id: Int): InPaintingHistory {
        return inPaintingHistoryRepository.getHistory(id = id)
    }
}