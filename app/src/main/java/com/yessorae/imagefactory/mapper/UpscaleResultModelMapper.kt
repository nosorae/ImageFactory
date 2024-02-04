package com.yessorae.imagefactory.mapper

import com.yessorae.common.replaceDomain
import com.yessorae.data.remote.stablediffusion.model.response.UpscaleDto
import com.yessorae.domain.model.UpscaleResultModel
import javax.inject.Inject

class UpscaleResultModelMapper @Inject constructor() {
    fun map(dto: UpscaleDto): UpscaleResultModel {
        return UpscaleResultModel(
            id = dto.id,
            outputUrl = dto.output.replaceDomain(),
            status = dto.status,
            generationTime = dto.generationTime
        )
    }
}
