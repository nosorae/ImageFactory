package com.yessorae.domain.usecase.inpainting

import com.yessorae.domain.model.inpainting.InPaintingRequest
import com.yessorae.domain.repository.PreferenceRepository
import com.yessorae.domain.repository.inpainting.InPaintingHistoryRepository
import javax.inject.Inject

class InsertInPaintingHistoryUseCase @Inject constructor(
    private val inPaintingHistoryRepository: InPaintingHistoryRepository,
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(request: InPaintingRequest): Long {
        val historyId = inPaintingHistoryRepository.insertRequestHistory(requestBody = request)
        preferenceRepository.setLastTxtToImageRequestHistoryId(historyId = historyId)
        return historyId
    }
}