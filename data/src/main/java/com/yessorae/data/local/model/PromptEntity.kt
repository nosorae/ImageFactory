package com.yessorae.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "prompt")
data class PromptEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "prompt_id")
    var promptId: Int = 0,
    val prompt: String,
    @ColumnInfo(name = "select_count")
    val selectCount: Int,
    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,
    @ColumnInfo(name = "updated_at")
    val updatedAt: LocalDateTime
)