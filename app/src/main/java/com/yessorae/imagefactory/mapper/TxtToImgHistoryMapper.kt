package com.yessorae.imagefactory.mapper

import com.yessorae.data.local.database.model.TxtToImgHistoryEntity
import com.yessorae.imagefactory.ui.model.TxtToImgHistory
import javax.inject.Inject

class TxtToImgHistoryMapper @Inject constructor(
    private val txtToImgResultMapper: TxtToImgResultMapper,
    private val txtToImgRequestMapper: TxtToImgRequestMapper
) {
    fun map(entities: List<TxtToImgHistoryEntity>): List<TxtToImgHistory> {
        return entities.map { entity ->
            map(entity = entity)
        }
    }

    fun map(entity: TxtToImgHistoryEntity): TxtToImgHistory {
        return TxtToImgHistory(
            id = entity.id,
            createdAt = entity.createdAt,
            request = txtToImgRequestMapper.map(entity = entity.request),
            result = txtToImgResultMapper.map(entity = entity.result),
            meta = txtToImgResultMapper.mapMeta(entity = entity.meta)
        )
    }
}