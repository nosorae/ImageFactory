package com.yessorae.domain.usecase.tti

import com.yessorae.domain.model.tti.TxtToImgHistory
import com.yessorae.domain.repository.tti.TxtToImgHistoryRepository
import javax.inject.Inject

class GetTxtToImgHistoryUseCase @Inject constructor(
    private val txtToImgHistoryRepository: TxtToImgHistoryRepository
) {
    suspend operator fun invoke(id: Int): TxtToImgHistory {
        return txtToImgHistoryRepository.getHistory(id = id)
    }
}

