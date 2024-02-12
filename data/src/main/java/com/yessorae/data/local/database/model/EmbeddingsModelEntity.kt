package com.yessorae.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yessorae.data.util.DBConstants
import com.yessorae.domain.model.parameter.EmbeddingsModel
import com.yessorae.domain.model.parameter.LoRaModel
import java.time.LocalDateTime

@Entity(tableName = DBConstants.TABLE_EMBEDDINGS_MODEL)
data class EmbeddingsModelEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(DBConstants.COL_IMG_URL)
    val imgUrl: String?,
    @ColumnInfo(DBConstants.COL_DISPLAY_NAME)
    val displayName: String,
    @ColumnInfo(DBConstants.COL_USED_AT)
    val usedAt: LocalDateTime
)

fun EmbeddingsModelEntity.asDomainModel(): EmbeddingsModel {
    return EmbeddingsModel(
        id = id,
        imgUrl = imgUrl,
        displayName = displayName
    )
}

fun EmbeddingsModel.asEntity(): EmbeddingsModelEntity {
    return EmbeddingsModelEntity(
        id = id,
        imgUrl = imgUrl,
        displayName = displayName,
        usedAt = LocalDateTime.now()
    )
}