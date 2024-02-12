package com.yessorae.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.yessorae.data.util.replaceDomain
import com.yessorae.data.remote.stablediffusion.model.response.TxtToImgDto
import com.yessorae.data.remote.stablediffusion.model.response.asEntity
import com.yessorae.domain.model.TxtToImgResult

data class ResultEntity(
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "generation_time")
    val generationTime: Double,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "output")
    val output: List<String>,
    @Embedded(prefix = "result_meta_data")
    val metaData: ResultMetaDataEntity?
)

fun ResultEntity.asDomainModel(): TxtToImgResult {
    return TxtToImgResult(
        id = id,
        outputUrls = output.map { it.replaceDomain() },
        status = status,
        generationTime = generationTime
    )
}

fun TxtToImgResult.asEntity(): ResultEntity {
    return ResultEntity(
        id = id,
        output = outputUrls,
        status = status,
        generationTime = generationTime ?: 0.0, // TODO:: check
        metaData = metaData?.asEntity()
    )
}
