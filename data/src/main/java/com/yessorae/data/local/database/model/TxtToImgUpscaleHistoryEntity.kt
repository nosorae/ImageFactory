package com.yessorae.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yessorae.data.util.DBConstants

@Entity(tableName = DBConstants.TABLE_TXT_TO_IMG_UPSCALE_HISTORY)
data class TxtToImgUpscaleHistoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "history_id")
    val historyId: Int,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "generation_time")
    val generationTime: Double,
    @ColumnInfo(name = "output")
    val output: String,
    @ColumnInfo(name = DBConstants.COL_SERVER_SYNC)
    val serverSync: Boolean = false
)
