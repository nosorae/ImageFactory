package com.yessorae.domain.repository

import java.time.LocalDateTime

interface PreferenceRepository {
    suspend fun setLastTxtToImageRequestHistoryId(historyId: Long)

    suspend fun getLastTxtToImageRequestHistoryId(): Long?

    suspend fun setLastModelUpdateTime()
    suspend fun getLastModelUpdateTime(): LocalDateTime?

    suspend fun setCompleteInitPromptData()

    suspend fun getCompleteInitPromptData(): Boolean
}