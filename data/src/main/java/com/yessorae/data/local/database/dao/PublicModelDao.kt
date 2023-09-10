package com.yessorae.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yessorae.data.local.database.model.PublicModelEntity
import com.yessorae.data.util.DBConstants

@Dao
interface PublicModelDao : BaseDao<PublicModelEntity> {
    @Query(
        """
            SELECT * FROM ${DBConstants.TABLE_PUBLIC_MODEL}
    """
    )
    suspend fun getPublicModelByCallCounts(): List<PublicModelEntity>
}