package com.yessorae.data.local.dao

import androidx.room.Query
import com.yessorae.data.util.DBConstants
import com.yessorae.data.local.model.PromptEntity

interface PromptDao : BaseDao<PromptEntity> {
    @Query("""
        SELECT * FROM ${DBConstants.TABLE_PROMPT} ORDER BY ${DBConstants.COL_SELECT_COUNT} DESC
    """)
    fun getPromptsOrderedBySelectCount(): List<PromptEntity>

    @Query("""
        SELECT * FROM ${DBConstants.TABLE_PROMPT} ORDER BY ${DBConstants.COL_UPDATED_AT} DESC
    """)
    fun getPromptsOrderedByUpdatedAt(): List<PromptEntity>
}