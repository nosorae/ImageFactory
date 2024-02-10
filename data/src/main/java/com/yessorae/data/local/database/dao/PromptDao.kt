package com.yessorae.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yessorae.data.local.database.model.PromptEntity
import com.yessorae.data.util.DBConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface PromptDao : BaseDao<PromptEntity> {
    @Query(
        """
        SELECT * FROM ${DBConstants.TABLE_PROMPT} WHERE ${DBConstants.COLPOSITIVE} = :isPositive ORDER BY ${DBConstants.COL_SELECT_COUNT} DESC LIMIT 50
    """
    )
    fun getPromptsOrderedBySelectCount(isPositive: Boolean): Flow<List<PromptEntity>>

    @Query(
        """
        SELECT * FROM ${DBConstants.TABLE_PROMPT} WHERE ${DBConstants.COLPOSITIVE} = :isPositive ORDER BY ${DBConstants.COL_UPDATED_AT} DESC LIMIT 50
    """
    )
    fun getPromptsOrderedByUpdatedAt(isPositive: Boolean): Flow<List<PromptEntity>>

    @Query(
        """
        SELECT * FROM ${DBConstants.TABLE_PROMPT} WHERE ${DBConstants.COLPOSITIVE} = :isPositive ORDER BY ${DBConstants.COL_CREATED_AT} DESC LIMIT 50
    """
    )
    fun getPromptsOrderedByCreatedAt(isPositive: Boolean): Flow<List<PromptEntity>>
}
