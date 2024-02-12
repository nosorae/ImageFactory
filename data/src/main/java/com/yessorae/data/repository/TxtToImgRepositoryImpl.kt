package com.yessorae.data.repository

import com.yessorae.data.remote.stablediffusion.api.TxtToImgApi
import com.yessorae.data.remote.stablediffusion.model.request.FetchQueuedImageRequestDto
import com.yessorae.data.remote.stablediffusion.model.request.asRequestDto
import com.yessorae.data.remote.stablediffusion.model.response.asDomainModel
import com.yessorae.data.util.handleResponse
import com.yessorae.domain.model.FetchQueuedImgResponse
import com.yessorae.domain.model.TxtToImgRequest
import com.yessorae.domain.model.TxtToImgResult
import com.yessorae.domain.repository.TxtToImgRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TxtToImgRepositoryImpl @Inject constructor(
    private val txtToImgApi: TxtToImgApi
) : TxtToImgRepository {
    override suspend fun generateImage(
        request: TxtToImgRequest
    ): TxtToImgResult {
        return txtToImgApi
            .generateImage(
                request = request.asRequestDto()
            )
            .handleResponse()
            .asDomainModel()
    }

    override suspend fun fetchQueuedImage(
        requestId: String
    ): FetchQueuedImgResponse {
        return txtToImgApi
            .fetchQueuedImage(
                request = FetchQueuedImageRequestDto(
                    requestId = requestId
                )
            )
            .handleResponse()
            .asDomainModel()
    }
}

