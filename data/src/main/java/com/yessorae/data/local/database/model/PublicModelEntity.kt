package com.yessorae.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yessorae.data.util.DBConstants
import com.yessorae.domain.model.PublicModel

// TODO:: SR-N SD 모델, LoRa 모델, Embeddings 모델 테이블 추가 분리
// 서버에서 받아온 데이터 그대로 저장되어있는 데이블
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
    val description: String?, // Openjourney is an open source Stable Diffusion fine tuned model on Midjourney images, by PromptHero
    @ColumnInfo(name = DBConstants.COL_SCREENSHOTS)
    val screenshots: String, // https://d1okzptojspljx.cloudfront.net/generations/14853540911669470514.png
    @ColumnInfo(name = DBConstants.COL_SERVER_SYNC)
    val serverSync: Boolean = false
)

fun PublicModelEntity.asDomainModel(): PublicModel {
    return PublicModel(
        modelId = modelId,
        status = status,
        createdAt = createdAt,
        instancePrompt = instancePrompt,
        apiCalls = apiCalls,
        modelCategory = modelCategory,
        isNsfw = isNsfw,
        featured = featured,
        modelName = modelName,
        description = description,
        screenshots = screenshots
    )
}
