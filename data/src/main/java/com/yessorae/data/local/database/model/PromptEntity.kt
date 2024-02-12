package com.yessorae.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yessorae.data.util.DBConstants
import com.yessorae.domain.model.parameter.Prompt
import java.time.LocalDateTime

@Entity(tableName = DBConstants.TABLE_PROMPT)
data class PromptEntity(
    @PrimaryKey
    @ColumnInfo(name = DBConstants.COL_PROMPT)
    val prompt: String,
    @ColumnInfo(name = DBConstants.COLPOSITIVE) val positive: Boolean = true,
    @ColumnInfo(name = DBConstants.COL_NSFW) val nsfw: Boolean = false,
    @ColumnInfo(name = DBConstants.COL_SELECT_COUNT) val selectCount: Int = 0,
    @ColumnInfo(name = DBConstants.COL_UPDATED_AT) val updatedAt: LocalDateTime,
    @ColumnInfo(name = DBConstants.COL_SERVER_SYNC) val serverSync: Boolean = false
)

fun PromptEntity.asDomainModel(): Prompt {
    return Prompt(
        positive = positive,
        prompt = prompt,
        nsfw = nsfw
    )
}

fun Prompt.asEntityModel(): PromptEntity {
    return PromptEntity(
        prompt = prompt,
        positive = positive,
        nsfw = nsfw,
        updatedAt = LocalDateTime.now()
    )
}