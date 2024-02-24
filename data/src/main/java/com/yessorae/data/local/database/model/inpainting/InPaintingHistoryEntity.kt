package com.yessorae.data.local.database.model.inpainting

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yessorae.data.util.DBConstants
import com.yessorae.domain.model.inpainting.InPaintingHistory
import java.time.LocalDateTime

@Entity(tableName = DBConstants.TABLE_IN_PAINTING_HISTORY)
data class InPaintingHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DBConstants.COL_ID)
    var id: Int = 0,
    @ColumnInfo(DBConstants.COL_CREATED_AT)
    val createdAt: LocalDateTime,
    @Embedded(prefix = DBConstants.PREFIX_REQUEST)
    val request: InPaintingRequestEntity,
    @Embedded(prefix = DBConstants.PREFIX_RESULT)
    val result: InPaintingResultEntity? = null,
    @ColumnInfo(name = DBConstants.COL_SERVER_SYNC)
    val serverSync: Boolean = false
)

fun InPaintingHistoryEntity.asDomainModel(): InPaintingHistory {
    return InPaintingHistory(
        id = id,
        createdAt = createdAt,
        request = request.asDomainModel(),
        result = result?.asDomainModel()
    )
}

fun InPaintingHistory.asEntity(): InPaintingHistoryEntity {
    return InPaintingHistoryEntity(
        id = id,
        createdAt = createdAt,
        request = request.asEntity(),
        result = result?.asEntity()
    )
}