package com.yessorae.domain.usecase

import com.yessorae.domain.model.TxtToImgHistory
import com.yessorae.domain.repository.TxtToImgHistoryRepository
import com.yessorae.domain.repository.TxtToImgRepository
import com.yessorae.domain.util.ProcessingErrorException
import com.yessorae.domain.util.ProcessingException
import com.yessorae.domain.util.StableDiffusionApiConstants
import javax.inject.Inject

class RequestTxtToImgUseCase @Inject constructor(
    private val txtToImgRepository: TxtToImgRepository,
    private val txtToImgHistoryRepository: TxtToImgHistoryRepository
) {
    suspend operator fun invoke(requestHistoryId: Long) {
        val oldHistory: TxtToImgHistory =
            txtToImgHistoryRepository.getTxtToImgHistory(id = requestHistoryId.toInt())

        val result = txtToImgRepository.generateImage(request = oldHistory.request)

        val newHistory = oldHistory.copy(
            result = result
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