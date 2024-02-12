package com.yessorae.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yessorae.data.local.database.model.PublicModelEntity
import com.yessorae.data.local.database.model.SDModelEntity
import com.yessorae.data.util.DBConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface SDModelDao : BaseDao<SDModelEntity> {
    @Query(
        """
            SELECT * FROM ${DBConstants.TABLE_SD_MODEL} ORDER BY ${DBConstants.COL_USED_AT} DESC LIMIT 10
        """
    )
    fun getSDModelsByUsedAt(): Flow<List<SDModelEntity>>
}