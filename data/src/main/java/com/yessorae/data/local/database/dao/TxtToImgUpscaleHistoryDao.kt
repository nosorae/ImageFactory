package com.yessorae.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yessorae.data.local.database.model.TxtToImgUpscaleHistoryEntity
import com.yessorae.data.util.DBConstants

@Dao
interface TxtToImgUpscaleHistoryDao : BaseDao<TxtToImgUpscaleHistoryEntity> {
    @Query(
        """
            SELECT * FROM ${DBConstants.TABLE_TXT_TO_IMG_UPSCALE_HISTORY} WHERE ${DBConstants.COL_HISTORY_ID} = :id
        """
    )
    suspend fun getUpscaleHistoryByHistoryId(id: Int): TxtToImgUpscaleHistoryEntity
}
