package com.yessorae.domain.usecase

import com.yessorae.domain.model.tti.TxtToImgHistory
import com.yessorae.domain.repository.TxtToImgHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTxtToImgHistoriesUseCase @Inject constructor(
    private val txtToImgHistoryRepository: TxtToImgHistoryRepository
) {
    operator fun invoke(): Flow<List<TxtToImgHistory>> {
        // TODO:: Paging 구현
        return txtToImgHistoryRepository.getHistories()
    }
}

