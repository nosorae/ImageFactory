package com.yessorae.imagefactory.mapper

import com.yessorae.data.remote.stablediffusion.model.response.UpscaleDto
import com.yessorae.imagefactory.ui.screen.main.tti.model.UpscaleResult
import javax.inject.Inject

class UpscaleResultModelMapper @Inject constructor() {
    fun map(dto: UpscaleDto): UpscaleResult {
        return UpscaleResult(
            id = dto.id,
            outputUrl = dto.output,
            status = dto.status,
            generationTime = dto.generationTime
        )
    }
}