package com.yessorae.domain.usecase

import com.yessorae.domain.model.tti.TxtToImgHistory
import com.yessorae.domain.repository.TxtToImgHistoryRepository
import com.yessorae.domain.util.StableDiffusionConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTxtToImgHistoriesOnlySuccessUseCase @Inject constructor(
    private val txtToImgHistoryRepository: TxtToImgHistoryRepository
) {
    operator fun invoke(): Flow<List<TxtToImgHistory>> {
        return txtToImgHistoryRepository.getHistories()
            .map { list ->
                list.filter { history ->
                    history.result?.status == StableDiffusionConstants.RESPONSE_SUCCESS ||
                            history.result?.outputUrls.isNullOrEmpty().not()
                }
            }
    }
}