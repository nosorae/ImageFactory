package com.yessorae.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yessorae.data.local.database.model.UpscaleHistoryEntity
import com.yessorae.data.util.DBConstants

@Dao
interface UpscaleHistoryDao : BaseDao<UpscaleHistoryEntity> {
    @Query(
        """
            SELECT * FROM ${DBConstants.TABLE_UPSCALE_HISTORY} WHERE ${DBConstants.COL_HISTORY_ID} = :id
        """
    )
    suspend fun getUpscaleHistoryByHistoryId(id: Int): UpscaleHistoryEntity
}
