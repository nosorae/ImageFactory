package com.yessorae.imagefactory.mapper

import com.yessorae.common.replaceDomain
import com.yessorae.data.remote.stablediffusion.model.response.TxtToImgDto
import com.yessorae.imagefactory.ui.screen.main.tti.model.TxtToImgResultModel
import javax.inject.Inject

class TxtToImgResultMapper @Inject constructor() {
    fun map(dto: TxtToImgDto): TxtToImgResultModel {
        return TxtToImgResultModel(
            id = dto.id,
            outputUrls = dto.output.map { it.replaceDomain() },
            status = dto.status,
            generationTime = dto.generationTime,
        )
    }

    fun map(
        id: Int,
        outputUrls: List<String>,
        status: String,
        generationTime: Double? = null
    ): TxtToImgResultModel {
        return TxtToImgResultModel(
            id = id,
            outputUrls = outputUrls,
            status = status,
            generationTime = generationTime,
        )
    }
}

