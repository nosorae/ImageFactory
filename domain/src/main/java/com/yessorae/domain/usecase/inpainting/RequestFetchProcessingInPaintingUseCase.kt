package com.yessorae.domain.usecase.inpainting

import com.yessorae.domain.model.inpainting.InPaintingHistory
import com.yessorae.domain.repository.inpainting.InPaintingHistoryRepository
import com.yessorae.domain.repository.inpainting.InPaintingRepository
import com.yessorae.domain.util.ProcessingErrorException
import com.yessorae.domain.util.StableDiffusionConstants
import javax.inject.Inject

class RequestFetchProcessingInPaintingUseCase @Inject constructor(
    private val inPaintingRepository: InPaintingRepository,
    private val inPaintingHistoryRepository: InPaintingHistoryRepository
) {
    suspend operator fun invoke(
        historyId: Int,
        requestId: String
    ): InPaintingHistory {
        val oldHistory = inPaintingHistoryRepository.getHistory(id = historyId)
        val result = inPaintingRepository.fetchQueuedImage(requestId = requestId)

        val newHistory = oldHistory.copy(
            result = oldHistory.result?.copy(
                status = result.status,
                outputUrls = result.output
            )
        )
        inPaintingHistoryRepository.updateHistory(newHistory)

        if (result.status == StableDiffusionConstants.RESPONSE_ERROR) {
            inPaintingHistoryRepository.deleteHistory(id = historyId)
            throw ProcessingErrorException(message = result.status)
        }

        return newHistory
    }
}