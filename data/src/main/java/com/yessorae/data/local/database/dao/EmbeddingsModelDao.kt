package com.yessorae.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yessorae.data.local.database.model.EmbeddingsModelEntity
import com.yessorae.data.util.DBConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface EmbeddingsModelDao : BaseDao<EmbeddingsModelEntity> {
    @Query(
        """
            SELECT * FROM ${DBConstants.TABLE_EMBEDDINGS_MODEL} ORDER BY ${DBConstants.COL_USED_AT} DESC LIMIT 10
    """
    )
    fun getEmbeddingsModelsByCallCounts(): Flow<List<EmbeddingsModelEntity>>
}
