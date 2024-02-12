package com.yessorae.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yessorae.data.util.DBConstants
import com.yessorae.domain.model.parameter.SDModel
import java.time.LocalDateTime

@Entity(tableName = DBConstants.TABLE_SD_MODEL)
data class SDModelEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(DBConstants.COL_IMG_URL)
    val imgUrl: String?,
    @ColumnInfo(DBConstants.COL_DISPLAY_NAME)
    val displayName: String,
    @ColumnInfo(DBConstants.COL_USED_AT)
    val usedAt: LocalDateTime
)

fun SDModelEntity.asDomainModel(): SDModel {
    return SDModel(
        id = id,
        imgUrl = imgUrl,
        displayName = displayName
    )
}

fun SDModel.asEntity(): SDModelEntity {
    return SDModelEntity(
        id = id,
        imgUrl = imgUrl,
        displayName = displayName,
        usedAt = LocalDateTime.now()
    )
}