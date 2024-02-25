package com.yessorae.domain.repository.inpainting

import com.yessorae.domain.model.FetchQueuedImgResponse
import com.yessorae.domain.model.inpainting.InPaintingRequest
import com.yessorae.domain.model.inpainting.InPaintingResult

interface InPaintingRepository {
    suspend fun generateImage(
        request: InPaintingRequest
    ): InPaintingResult

    suspend fun fetchQueuedImage(
        requestId: String
    ): FetchQueuedImgResponse
}