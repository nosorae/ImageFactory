package com.yessorae.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yessorae.data.util.DBConstants
import com.yessorae.domain.model.TxtToImgHistory
import java.time.LocalDateTime

@Entity(tableName = DBConstants.TABLE_TXT_TO_IMG_HISTORY)
data class TxtToImgEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DBConstants.COL_ID)
    var id: Int = 0,
    @ColumnInfo(DBConstants.COL_CREATED_AT)
    val createdAt: LocalDateTime,
    @Embedded(prefix = DBConstants.PREFIX_REQUEST)
    val request: TxtToImgRequestEntity,
    @Embedded(prefix = DBConstants.PREFIX_RESULT)
    val result: ResultEntity? = null,
    @ColumnInfo(name = DBConstants.COL_SERVER_SYNC)
    val serverSync: Boolean = false
)

fun TxtToImgEntity.asDomainModel(): TxtToImgHistory {
    return TxtToImgHistory(
        id = id,
        createdAt = createdAt,
        request = request.asDomainModel(),
        result = result?.asDomainModel(),
    )
}

fun TxtToImgHistory.asEntity(): TxtToImgEntity {
    return TxtToImgEntity(
        id = id,
        createdAt = createdAt,
        request = request.asEntity(),
        result = result?.asEntity(),
    )
}
