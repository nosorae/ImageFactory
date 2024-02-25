package com.yessorae.domain.repository.tti

import com.yessorae.domain.model.FetchQueuedImgResponse
import com.yessorae.domain.model.tti.TxtToImgRequest
import com.yessorae.domain.model.tti.TxtToImgResult

interface TxtToImgRepository {
    suspend fun generateImage(
        request: TxtToImgRequest
    ): TxtToImgResult

    suspend fun fetchQueuedImage(
        requestId: String
    ): FetchQueuedImgResponse
}

