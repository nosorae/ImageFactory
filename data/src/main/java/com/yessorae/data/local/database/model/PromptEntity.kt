package com.yessorae.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yessorae.data.util.DBConstants
import java.time.LocalDateTime

@Entity(tableName = DBConstants.TABLE_PROMPT)
data class PromptEntity(
    @PrimaryKey
    @ColumnInfo(name = DBConstants.COL_PROMPT)
    val prompt: String,
    @ColumnInfo(name = DBConstants.COLPOSITIVE)
    val positive: Boolean = true,
    @ColumnInfo(name = DBConstants.COL_SELECT_COUNT)
    val selectCount: Int = 0,
    @ColumnInfo(name = DBConstants.COL_CREATED_AT)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = DBConstants.COL_UPDATED_AT)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = DBConstants.COL_SERVER_SYNC)
    val serverSync: Boolean = false
)


