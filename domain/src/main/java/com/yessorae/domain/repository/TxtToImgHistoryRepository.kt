package com.yessorae.domain.repository

import com.yessorae.domain.model.tti.TxtToImgHistory
import kotlinx.coroutines.flow.Flow

interface TxtToImgHistoryRepository {
    suspend fun insertRequestHistory(
        requestBody: TxtToImgHistory
    ): Long

    suspend fun getTxtToImgHistory(
        id: Int
    ): TxtToImgHistory

    fun getHistories(): Flow<List<TxtToImgHistory>>

    suspend fun updateHistory(
        history: TxtToImgHistory
    )

    suspend fun deleteHistory(
        id: Int
    )
}

