package com.yessorae.data.repository

import com.yessorae.data.remote.stablediffusion.api.StableDiffusionApi
import com.yessorae.data.remote.stablediffusion.model.request.FetchQueuedImageRequestDto
import com.yessorae.data.remote.stablediffusion.model.request.asDto
import com.yessorae.data.remote.stablediffusion.model.response.asDomainModel
import com.yessorae.data.util.handleResponse
import com.yessorae.domain.model.FetchQueuedImgResponse
import com.yessorae.domain.model.tti.TxtToImgRequest
import com.yessorae.domain.model.tti.TxtToImgResult
import com.yessorae.domain.repository.tti.TxtToImgRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TxtToImgRepositoryImpl @Inject constructor(
    private val stableDiffusionApi: StableDiffusionApi
) : TxtToImgRepository {
    override suspend fun generateImage(
        request: TxtToImgRequest
    ): TxtToImgResult {
        return stableDiffusionApi
            .generateImage(
                request = request.asDto()
            )
            .handleResponse()
            .asDomainModel()
    }

    override suspend fun fetchQueuedImage(
        requestId: String
    ): FetchQueuedImgResponse {
        return stableDiffusionApi
            .fetchQueuedImage(
                request = FetchQueuedImageRequestDto(
                    requestId = requestId
                )
            )
            .handleResponse()
            .asDomainModel()
    }
}
