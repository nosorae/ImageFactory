package com.yessorae.imagefactory.mapper

import com.yessorae.data.remote.model.response.TxtToImgDto
import com.yessorae.imagefactory.ui.screen.tti.model.TxtToImgResultModel
import javax.inject.Inject

class TxtToImgResultMapper @Inject constructor() {
    fun map(dto: TxtToImgDto): TxtToImgResultModel {
        return TxtToImgResultModel(
            id = dto.id,
            outputUrls = dto.output,
            status = dto.status,
            generationTime = dto.generationTime,
        )
    }

    fun map(id: Int, outputUrls: List<String>, status: String, generationTime: Double?): TxtToImgResultModel {
        return TxtToImgResultModel(
            id = id,
            outputUrls = outputUrls,
            status = status,
            generationTime = generationTime,
        )
    }
}