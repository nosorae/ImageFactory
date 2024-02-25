package com.yessorae.data.repository

import com.yessorae.data.remote.stablediffusion.api.StableDiffusionApi
import com.yessorae.data.remote.stablediffusion.model.request.FetchQueuedImageRequestDto
import com.yessorae.data.remote.stablediffusion.model.request.asDto
import com.yessorae.data.remote.stablediffusion.model.response.asDomainModel
import com.yessorae.data.util.handleResponse
import com.yessorae.domain.model.FetchQueuedImgResponse
import com.yessorae.domain.model.inpainting.InPaintingRequest
import com.yessorae.domain.model.inpainting.InPaintingResult
import com.yessorae.domain.repository.inpainting.InPaintingRepository
import javax.inject.Inject

class InPaintingRepositoryImpl @Inject constructor(
    private val stableDiffusionApi: StableDiffusionApi
) : InPaintingRepository {
    override suspend fun generateImage(
        request: InPaintingRequest
    ): InPaintingResult {
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