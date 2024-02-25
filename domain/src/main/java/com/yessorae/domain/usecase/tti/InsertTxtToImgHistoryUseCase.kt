package com.yessorae.domain.usecase.tti

import com.yessorae.domain.model.tti.TxtToImgHistory
import com.yessorae.domain.repository.PreferenceRepository
import com.yessorae.domain.repository.tti.TxtToImgHistoryRepository
import javax.inject.Inject

class InsertTxtToImgHistoryUseCase @Inject constructor(
    private val txtToImgHistoryRepository: TxtToImgHistoryRepository,
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(request: TxtToImgHistory): Long {
        val historyId = txtToImgHistoryRepository.insertRequestHistory(requestBody = request)
        preferenceRepository.setLastTxtToImageRequestHistoryId(historyId = historyId)
        return historyId
    }
}
