package com.yessorae.domain.usecase.inpainting

import com.yessorae.domain.model.inpainting.InPaintingHistory
import com.yessorae.domain.model.inpainting.InPaintingResult
import com.yessorae.domain.repository.inpainting.InPaintingHistoryRepository
import com.yessorae.domain.repository.inpainting.InPaintingRepository
import com.yessorae.domain.util.ProcessingErrorException
import com.yessorae.domain.util.StableDiffusionConstants
import javax.inject.Inject

class RequestInPaintingUseCase @Inject constructor(
    private val inPaintingRepository: InPaintingRepository,
    private val inPaintingHistoryRepository: InPaintingHistoryRepository
) {
    suspend operator fun invoke(requestHistoryId: Int): InPaintingResult {
        val oldHistory: InPaintingHistory =
            inPaintingHistoryRepository.getHistory(id = requestHistoryId)

        val result = inPaintingRepository.generateImage(request = oldHistory.request)

        val newHistory = oldHistory.copy(result = result)
        inPaintingHistoryRepository.updateHistory(newHistory)

        if (result.status == StableDiffusionConstants.RESPONSE_ERROR) {
            inPaintingHistoryRepository.deleteHistory(id = requestHistoryId)
            throw ProcessingErrorException(message = result.status)
        }

        return result
    }
}