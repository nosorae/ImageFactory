package com.yessorae.data.local.dao

import androidx.room.Query
import com.yessorae.data.util.DBConstants
import com.yessorae.data.local.model.PromptEntity

interface PromptDao : BaseDao<PromptEntity> {
    @Query("""
        SELECT * FROM ${DBConstants.TABLE_PROMPT} ORDER BY ${DBConstants.COL_SELECT_COUNT} DESC LIMIT 50
    """)
    fun getPromptsOrderedBySelectCount(): List<PromptEntity>

    @Query("""
        SELECT * FROM ${DBConstants.TABLE_PROMPT} ORDER BY ${DBConstants.COL_UPDATED_AT} DESC LIMIT 50
    """)
    fun getPromptsOrderedByUpdatedAt(): List<PromptEntity>

    @Query("""
        SELECT * FROM ${DBConstants.TABLE_PROMPT} ORDER BY ${DBConstants.COL_CREATED_AT} DESC LIMIT 50
    """)
    fun getPromptsOrderedByCreatedAt(): List<PromptEntity>
}