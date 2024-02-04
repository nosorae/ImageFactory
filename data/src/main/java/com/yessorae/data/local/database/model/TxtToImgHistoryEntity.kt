package com.yessorae.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequestDto
import com.yessorae.data.remote.stablediffusion.model.request.asRequestDto
import com.yessorae.data.remote.stablediffusion.model.response.MetaDataResponseDto
import com.yessorae.data.util.DBConstants
import com.yessorae.domain.model.TxtToImgHistory
import java.time.LocalDateTime

@Entity(tableName = DBConstants.TABLE_TXT_TO_IMG_HISTORY)
data class TxtToImgHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DBConstants.COL_ID)
    var id: Int = 0,
    @ColumnInfo(DBConstants.COL_CREATED_AT)
    val createdAt: LocalDateTime,
    @Embedded(prefix = DBConstants.PREFIX_REQUEST)
    val request: TxtToImgRequestEntity,
    @Embedded(prefix = DBConstants.PREFIX_META)
    val meta: ResultMetaDataEntity? = null,
    @Embedded(prefix = DBConstants.PREFIX_RESULT)
    val result: ResultEntity? = null,
    @ColumnInfo(name = DBConstants.COL_SERVER_SYNC)
    val serverSync: Boolean = false
)

fun TxtToImgHistoryEntity.asDomainModel(): TxtToImgHistory {
    return TxtToImgHistory(
        id = id,
        createdAt = createdAt,
        request = request.asDomainModel(),
        result = result?.asDomainModel(),
        meta = meta?.asDomainModel()
    )
}

fun TxtToImgHistory.asEntity(): TxtToImgHistoryEntity {
    return TxtToImgHistoryEntity(
        id = id,
        createdAt = createdAt,
        request = request.asEntity(),
        result = result?.asEntity(),
        meta = meta?.asEntity()
    )
}

// TODO:: UseCase에서 해결
fun mapNotResultNull(entities: List<TxtToImgHistoryEntity>): List<TxtToImgHistory> {
    return entities.mapNotNull { entity ->
        val resultEntity = entity.result
        val metaEntity = entity.meta
        if (resultEntity != null && metaEntity != null) {
            TxtToImgHistory(
                id = entity.id,
                createdAt = entity.createdAt,
                request = entity.request.asDomainModel(),
                result = resultEntity.asDomainModel(),
                meta = metaEntity.asDomainModel()
            )
        } else {
            null
        }
    }
}