package com.yessorae.domain.repository

import com.yessorae.domain.model.MetaDataResponse
import com.yessorae.domain.model.TxtToImgHistory
import com.yessorae.domain.model.TxtToImgRequest
import com.yessorae.domain.model.TxtToImgResult
import kotlinx.coroutines.flow.Flow

interface TxtToImgHistoryRepository {
    suspend fun insertRequestHistory(
        requestBody: TxtToImgHistory
    ): Long

    suspend fun updateRequestHistory(
        id: Int,
        result: TxtToImgResult,
        meta: MetaDataResponse
    )

    suspend fun getTxtToImgHistory(
        id: Int
    ): TxtToImgHistory

    suspend fun getLastTxtToImgHistory(): TxtToImgHistory?

    fun getHistories(): Flow<List<TxtToImgHistory>>

    suspend fun deleteHistory(
        id: Int
    )

    suspend fun fetchQueuedImage(
        id: Int,
        requestId: String
    ): TxtToImgHistory
}
