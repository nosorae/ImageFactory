package com.yessorae.domain.usecase.inpainting

import com.yessorae.domain.repository.inpainting.InPaintingHistoryRepository
import javax.inject.Inject

class DeleteInPaintingHistoryUseCase @Inject constructor(
    private val inPaintingHistoryRepository: InPaintingHistoryRepository
) {
    suspend operator fun invoke(historyId: Int) {
        inPaintingHistoryRepository.deleteHistory(id = historyId)
    }
}