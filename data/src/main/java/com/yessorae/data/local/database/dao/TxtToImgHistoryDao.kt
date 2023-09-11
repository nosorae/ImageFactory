package com.yessorae.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yessorae.data.local.database.model.TxtToImgHistoryEntity
import com.yessorae.data.util.DBConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface TxtToImgHistoryDao : BaseDao<TxtToImgHistoryEntity> {
    @Query(
        """
            SELECT * FROM ${DBConstants.TABLE_TXT_TO_IMG_HISTORY}
        """
    )
    fun getTxtToImgHistoryModel(): Flow<List<TxtToImgHistoryEntity>>


}