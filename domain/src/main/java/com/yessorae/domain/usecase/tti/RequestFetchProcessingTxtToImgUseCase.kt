package com.yessorae.domain.usecase.tti

import com.yessorae.domain.model.tti.TxtToImgHistory
import com.yessorae.domain.repository.tti.TxtToImgHistoryRepository
import com.yessorae.domain.repository.tti.TxtToImgRepository
import com.yessorae.domain.util.ProcessingErrorException
import com.yessorae.domain.util.StableDiffusionConstants
import javax.inject.Inject

class RequestFetchProcessingTxtToImgUseCase @Inject constructor(
    private val txtToImgRepository: TxtToImgRepository,
    private val txtToImgHistoryRepository: TxtToImgHistoryRepository
) {
    suspend operator fun invoke(
        historyId: Int,
        requestId: String
    ): TxtToImgHistory {
        val oldHistory = txtToImgHistoryRepository.getHistory(id = historyId)
        val result = txtToImgRepository.fetchQueuedImage(requestId = requestId)

        val newHistory = oldHistory.copy(
            result = oldHistory.result?.copy(
                status = result.status,
                outputUrls = result.output
            )
        )
        txtToImgHistoryRepository.updateHistory(newHistory)

        if (result.status == StableDiffusionConstants.RESPONSE_ERROR) {
            txtToImgHistoryRepository.deleteHistory(id = historyId)
            throw ProcessingErrorException(message = result.status)
        }

        return newHistory
    }
}

