package com.yessorae.data.local.database.model.inpainting

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.yessorae.data.util.DBConstants
import com.yessorae.data.util.replaceDomain
import com.yessorae.domain.model.inpainting.InPaintingResult

data class InPaintingResultEntity(
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "generation_time")
    val generationTime: Double,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "output")
    val output: List<String>,
    @Embedded(prefix = DBConstants.PREFIX_META)
    val metaData: InPaintingResultMetaDataEntity?
)

fun InPaintingResultEntity.asDomainModel(): InPaintingResult {
    return InPaintingResult(
        id = id,
        outputUrls = output.map { it.replaceDomain() },
        status = status,
        generationTime = generationTime
    )
}

fun InPaintingResult.asEntity(): InPaintingResultEntity {
    return InPaintingResultEntity(
        id = id,
        output = outputUrls,
        status = status,
        generationTime = generationTime ?: 0.0, // TODO:: check
        metaData = metaData?.asEntity()
    )
}