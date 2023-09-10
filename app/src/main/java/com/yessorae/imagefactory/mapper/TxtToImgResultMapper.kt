package com.yessorae.imagefactory.mapper

import com.yessorae.common.replaceDomain
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequestBody
import com.yessorae.data.remote.stablediffusion.model.response.MetaDataDto
import com.yessorae.data.remote.stablediffusion.model.response.TxtToImgDto
import com.yessorae.imagefactory.ui.screen.result.model.TxtToImgRequest
import com.yessorae.imagefactory.ui.screen.result.model.TxtToImgResultModel
import javax.inject.Inject

class TxtToImgResultMapper @Inject constructor() {
    fun map(
        dto: TxtToImgDto
    ): TxtToImgResultModel {
        return TxtToImgResultModel(
            id = dto.id,
            outputUrls = dto.output.map { it.replaceDomain() },
            status = dto.status,
            generationTime = dto.generationTime
        )
    }

    fun map(
        id: Int,
        outputUrls: List<String>,
        status: String,
        generationTime: Double? = null,
        request: TxtToImgRequestBody
    ): TxtToImgResultModel {
        return TxtToImgResultModel(
            id = id,
            outputUrls = outputUrls,
            status = status,
            generationTime = generationTime,
        )
    }

    fun mapToMetaDataFromUserRequest(
        userRequest: TxtToImgRequest,
        dto: MetaDataDto
    ): TxtToImgRequest {
        return userRequest.copy(
            prompt = dto.prompt,
            negativePrompt = dto.negativePrompt
        )
    }
}

