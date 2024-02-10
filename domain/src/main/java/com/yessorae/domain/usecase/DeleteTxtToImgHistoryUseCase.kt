package com.yessorae.domain.usecase

import com.yessorae.domain.repository.TxtToImgHistoryRepository
import javax.inject.Inject

class DeleteTxtToImgHistoryUseCase @Inject constructor(
    private val txtToImgHistoryRepository: TxtToImgHistoryRepository
) {
    suspend operator fun invoke(historyId: Int) {
        txtToImgHistoryRepository.deleteHistory(id = historyId)
    }
}