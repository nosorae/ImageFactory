package com.yessorae.imagefactory.mapper

import com.yessorae.data.remote.stablediffusion.model.response.UpscaleDto
import com.yessorae.imagefactory.ui.screen.main.tti.model.UpscaleResultModel
import javax.inject.Inject

class UpscaleResultModelMapper @Inject constructor() {
    fun map(dto: UpscaleDto, beforeUrl: String): UpscaleResultModel {
        return UpscaleResultModel(
            id = dto.id,
            beforeUrl = beforeUrl,
            outputUrl = dto.output,
            status = dto.status,
            generationTime = dto.generationTime
        )
    }
}