package com.yessorae.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yessorae.data.local.database.model.LoRaModelEntity
import com.yessorae.data.local.database.model.PublicModelEntity
import com.yessorae.data.util.DBConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface LoRaModelDao : BaseDao<LoRaModelEntity> {
    @Query(
        """
            SELECT * FROM ${DBConstants.TABLE_LORA_MODEL} ORDER BY ${DBConstants.COL_USED_AT} DESC LIMIT 10
    """
    )
    fun getLoRaModelsByUsedAt(): Flow<List<LoRaModelEntity>>
}