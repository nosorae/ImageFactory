package com.yessorae.domain.usecase

import com.yessorae.domain.model.TxtToImgHistory
import com.yessorae.domain.repository.TxtToImgHistoryRepository
import javax.inject.Inject

class InsertTxtToImgHistoryUseCase @Inject constructor(
    private val txtToImgHistoryRepository: TxtToImgHistoryRepository
) {
    suspend operator fun invoke(request: TxtToImgHistory): Long {
        return txtToImgHistoryRepository.insertRequestHistory(requestBody = request)
    }
}