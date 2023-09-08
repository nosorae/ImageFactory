package com.yessorae.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yessorae.data.util.DBConstants
import com.yessorae.data.local.database.model.PromptEntity

@Dao
interface PromptDao : BaseDao<PromptEntity> {
    @Query(
        """
        SELECT * FROM ${DBConstants.TABLE_PROMPT} WHERE ${DBConstants.COLPOSITIVE} = :isPositive ORDER BY ${DBConstants.COL_SELECT_COUNT} DESC LIMIT 50
    """
    )
    suspend fun getPromptsOrderedBySelectCount(isPositive: Boolean): List<PromptEntity>

    @Query(
        """
        SELECT * FROM ${DBConstants.TABLE_PROMPT} WHERE ${DBConstants.COLPOSITIVE} = :isPositive ORDER BY ${DBConstants.COL_UPDATED_AT} DESC LIMIT 50
    """
    )
    suspend fun getPromptsOrderedByUpdatedAt(isPositive: Boolean): List<PromptEntity>

    @Query(
        """
        SELECT * FROM ${DBConstants.TABLE_PROMPT} WHERE ${DBConstants.COLPOSITIVE} = :isPositive ORDER BY ${DBConstants.COL_CREATED_AT} DESC LIMIT 50
    """
    )
    suspend fun getPromptsOrderedByCreatedAt(isPositive: Boolean): List<PromptEntity>
}