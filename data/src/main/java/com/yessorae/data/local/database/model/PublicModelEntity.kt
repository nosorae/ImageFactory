package com.yessorae.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yessorae.common.trueOrFalse
import com.yessorae.data.remote.stablediffusion.model.response.PublicModelItem
import com.yessorae.data.util.DBConstants

@Entity(tableName = DBConstants.TABLE_PUBLIC_MODEL)
data class PublicModelEntity(
    @PrimaryKey
    @ColumnInfo(name = DBConstants.COL_MODEL_ID)
    val modelId: String, // midjourney
    @ColumnInfo(name = DBConstants.COL_STATUS)
    val status: String, // model_ready
    @ColumnInfo(name = DBConstants.COL_CREATED_AT)
    val createdAt: String?, // null
    @ColumnInfo(name = DBConstants.COL_INSTANCE_PROMPT)
    val instancePrompt: String?, // mdjrny-v4 style
    @ColumnInfo(name = DBConstants.COL_API_CALLS)
    val apiCalls: Long?, // "1690106"
    @ColumnInfo(name = DBConstants.COL_MODEL_CATEGORY)
    val modelCategory: String, // stable_diffusion
    @ColumnInfo(name = DBConstants.COL_IS_NSFW)
    val isNsfw: Boolean?, // yes || no
    @ColumnInfo(name = DBConstants.COL_FEATURED)
    val featured: Boolean?, // yes || no
    @ColumnInfo(name = DBConstants.COL_MODEL_NAME)
    val modelName: String, // MidJourney V4
    @ColumnInfo(name = DBConstants.COL_DESCRIPTION)
    val description: String, // Openjourney is an open source Stable Diffusion fine tuned model on Midjourney images, by PromptHero
    @ColumnInfo(name = DBConstants.COL_SCREENSHOTS)
    val screenshots: String // https://d1okzptojspljx.cloudfront.net/generations/14853540911669470514.png
)

fun PublicModelItem.mapToEntity(): PublicModelEntity {
    return PublicModelEntity(
        modelId = this.modelId,
        status = this.status,
        createdAt = this.createdAt?.toString(),
        instancePrompt = this.instancePrompt,
        apiCalls = try {
            this.apiCalls?.toLong()
        } catch (e: Exception) {
            null
        },
        modelCategory = this.modelCategory,
        isNsfw = this.isNsfw?.trueOrFalse(),
        featured = this.featured?.trueOrFalse(),
        modelName = this.modelName,
        description = this.description,
        screenshots = this.screenshots
    )
}
