package com.yessorae.domain.repository

import com.yessorae.domain.model.FetchQueuedImgResponse
import com.yessorae.domain.model.TxtToImgHistory
import com.yessorae.domain.model.TxtToImgRequest
import com.yessorae.domain.model.TxtToImgResult
import com.yessorae.domain.model.UpscaleResult
import com.yessorae.domain.util.FireStorageConstants
import com.yessorae.domain.util.ImageFactoryException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.util.UUID

interface TxtToImgRepository {
    suspend fun generateImage(
        request: TxtToImgRequest
    ): TxtToImgResult

    suspend fun fetchQueuedImage(
        requestId: String
    ): FetchQueuedImgResponse
}