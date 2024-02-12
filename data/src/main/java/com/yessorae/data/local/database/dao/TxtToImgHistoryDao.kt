package com.yessorae.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yessorae.data.local.database.model.TxtToImgEntity
import com.yessorae.data.util.DBConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface TxtToImgHistoryDao : BaseDao<TxtToImgEntity> {
    @Query(
        """
            SELECT * FROM ${DBConstants.TABLE_TXT_TO_IMG_HISTORY}
        """
    )
    fun getTxtToImgHistoryModels(): Flow<List<TxtToImgEntity>>

    @Query(
        """
            SELECT * FROM ${DBConstants.TABLE_TXT_TO_IMG_HISTORY} WHERE ${DBConstants.COL_ID} = :id  
        """
    )
    suspend fun getTxtToImgHistoryModel(id: Int): TxtToImgEntity

    @Query("DELETE FROM ${DBConstants.TABLE_TXT_TO_IMG_HISTORY} WHERE ${DBConstants.COL_ID} = :id")
    suspend fun deleteById(id: Int)
}
