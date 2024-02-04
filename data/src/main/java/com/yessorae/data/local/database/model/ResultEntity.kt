package com.yessorae.data.local.database.model

import androidx.room.ColumnInfo
import com.yessorae.data.util.replaceDomain
import com.yessorae.data.remote.stablediffusion.model.response.TxtToImgDto
import com.yessorae.domain.model.TxtToImgResult

data class ResultEntity(
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "generation_time")
    val generationTime: Double,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "output")
    val output: List<String>
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
        generationTime = generationTime ?: 0.0 // TODO:: check
    )
}

fun TxtToImgDto.asResultEntity(): ResultEntity {
    return ResultEntity(
        status = this.status,
        generationTime = this.generationTime,
        id = this.id,
        output = this.output
    )
}

fun ResultEntity.asDto(metaDataDto: ResultMetaDataEntity): TxtToImgDto {
    return TxtToImgDto(
        status = this.status,
        generationTime = this.generationTime,
        id = this.id,
        output = this.output,
        meta = metaDataDto.asDto()
    )
}

