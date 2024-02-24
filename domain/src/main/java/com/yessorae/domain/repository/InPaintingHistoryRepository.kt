package com.yessorae.domain.repository

import com.yessorae.domain.model.inpainting.InPaintingHistory
import com.yessorae.domain.model.inpainting.InPaintingRequest
import kotlinx.coroutines.flow.Flow

interface InPaintingHistoryRepository {
    suspend fun insertRequestHistory(
        requestBody: InPaintingRequest
    ): Long

    suspend fun getHistory(
        id: Int
    ): InPaintingHistory

    fun getHistories(): Flow<List<InPaintingHistory>>

    suspend fun updateHistory(
        history: InPaintingHistory
    )

    suspend fun deleteHistory(
        id: Int
    )
}