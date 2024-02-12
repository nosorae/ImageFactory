package com.yessorae.domain.usecase

import com.yessorae.domain.model.TxtToImgHistory
import com.yessorae.domain.repository.TxtToImgHistoryRepository
import javax.inject.Inject

class GetTxtToImgHistoryUseCase @Inject constructor(
    private val txtToImgHistoryRepository: TxtToImgHistoryRepository
) {
    suspend operator fun invoke(id: Int): TxtToImgHistory {
        return txtToImgHistoryRepository.getTxtToImgHistory(id = id)
    }
}