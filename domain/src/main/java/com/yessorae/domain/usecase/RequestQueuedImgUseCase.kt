package com.yessorae.domain.usecase

import com.yessorae.domain.repository.TxtToImgHistoryRepository
import com.yessorae.domain.repository.TxtToImgRepository
import com.yessorae.domain.util.ProcessingErrorException
import com.yessorae.domain.util.ProcessingException
import com.yessorae.domain.util.StableDiffusionApiConstants
import javax.inject.Inject

class RequestQueuedImgUseCase @Inject constructor(
    private val txtToImgRepository: TxtToImgRepository,
    private val txtToImgHistoryRepository: TxtToImgHistoryRepository
) {
    suspend operator fun invoke(
        historyId: Int,
        requestId: String
    ) {
        val oldHistory = txtToImgHistoryRepository.getTxtToImgHistory(id = historyId)
        val result = txtToImgRepository.fetchQueuedImage(requestId = requestId)

        val newHistory = oldHistory.copy(
            result = oldHistory.result?.copy(
                status = result.status,
                outputUrls = result.output
            )
        )
        txtToImgHistoryRepository.updateHistory(newHistory)

        when (result.status) {
            StableDiffusionApiConstants.RESPONSE_PROCESSING -> {
                throw ProcessingException
            }

            StableDiffusionApiConstants.RESPONSE_ERROR -> {
                throw ProcessingErrorException(message = result.status)
            }
        }
    }
}