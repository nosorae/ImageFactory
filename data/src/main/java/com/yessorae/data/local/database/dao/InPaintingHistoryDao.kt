package com.yessorae.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yessorae.data.local.database.model.inpainting.InPaintingHistoryEntity
import com.yessorae.data.util.DBConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface InPaintingHistoryDao : BaseDao<InPaintingHistoryEntity> {
    @Query(
        """
            SELECT * FROM ${DBConstants.TABLE_IN_PAINTING_HISTORY}
        """
    )
    fun getHistoryModels(): Flow<List<InPaintingHistoryEntity>>

    @Query(
        """
            SELECT * FROM ${DBConstants.TABLE_IN_PAINTING_HISTORY} WHERE ${DBConstants.COL_ID} = :id  
        """
    )
    suspend fun getHistoryModel(id: Int): InPaintingHistoryEntity

    @Query("DELETE FROM ${DBConstants.TABLE_IN_PAINTING_HISTORY} WHERE ${DBConstants.COL_ID} = :id")
    suspend fun deleteById(id: Int)
}