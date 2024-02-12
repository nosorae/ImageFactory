package com.yessorae.domain.usecase

import com.yessorae.domain.model.TxtToImgHistory
import com.yessorae.domain.model.TxtToImgResult
import com.yessorae.domain.repository.TxtToImgHistoryRepository
import com.yessorae.domain.repository.TxtToImgRepository
import com.yessorae.domain.util.ProcessingErrorException
import com.yessorae.domain.util.StableDiffusionConstants
import javax.inject.Inject

class RequestTxtToImgUseCase @Inject constructor(
    private val txtToImgRepository: TxtToImgRepository,
    private val txtToImgHistoryRepository: TxtToImgHistoryRepository
) {
    suspend operator fun invoke(requestHistoryId: Int): TxtToImgResult {
        val oldHistory: TxtToImgHistory =
            txtToImgHistoryRepository.getTxtToImgHistory(id = requestHistoryId)

        val result = txtToImgRepository.generateImage(request = oldHistory.request)

        val newHistory = oldHistory.copy(result = result)
        txtToImgHistoryRepository.updateHistory(newHistory)

        if (result.status == StableDiffusionConstants.RESPONSE_ERROR) {
            txtToImgHistoryRepository.deleteHistory(id = requestHistoryId)
            throw ProcessingErrorException(message = result.status)
        }

        return result
    }
}

