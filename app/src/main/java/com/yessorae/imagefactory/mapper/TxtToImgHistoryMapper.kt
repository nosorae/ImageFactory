package com.yessorae.imagefactory.mapper

import com.yessorae.data.local.database.model.TxtToImgHistoryEntity
import com.yessorae.imagefactory.ui.model.TxtToImgHistory
import javax.inject.Inject

class TxtToImgHistoryMapper @Inject constructor(
    private val txtToImgResultMapper: TxtToImgResultMapper,
    private val txtToImgRequestMapper: TxtToImgRequestMapper
) {
    fun mapNotResultNull(entities: List<TxtToImgHistoryEntity>): List<TxtToImgHistory> {
        return entities.mapNotNull { entity ->
            val resultEntity = entity.result
            val metaEntity = entity.meta
            if (resultEntity != null && metaEntity != null) {
                TxtToImgHistory(
                    id = entity.id,
                    createdAt = entity.createdAt,
                    request = txtToImgRequestMapper.map(entity = entity.request),
                    result = txtToImgResultMapper.map(entity = resultEntity),
                    meta = txtToImgResultMapper.mapMeta(entity = metaEntity)
                )
            } else {
                null
            }
        }
    }

    fun map(entity: TxtToImgHistoryEntity): TxtToImgHistory {
        return TxtToImgHistory(
            id = entity.id,
            createdAt = entity.createdAt,
            request = txtToImgRequestMapper.map(entity = entity.request),
            result = entity.result?.let { txtToImgResultMapper.map(entity = it) },
            meta = entity.meta?.let { txtToImgResultMapper.mapMeta(entity = it) }
        )
    }
}
