package com.yessorae.imagefactory.mapper

import com.yessorae.common.replaceDomain
import com.yessorae.data.local.database.model.RequestBodyEntity
import com.yessorae.data.local.database.model.ResultEntity
import com.yessorae.data.local.database.model.ResultMetaDataEntity
import com.yessorae.data.local.database.model.asEntity
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequestBody
import com.yessorae.data.remote.stablediffusion.model.response.MetaDataDto
import com.yessorae.data.remote.stablediffusion.model.response.TxtToImgDto
import com.yessorae.imagefactory.ui.model.TxtToImgRequest
import com.yessorae.imagefactory.ui.model.TxtToImgResult
import com.yessorae.imagefactory.ui.model.TxtToImgResultMetaData
import javax.inject.Inject

class TxtToImgResultMapper @Inject constructor() {
    fun map(
        dto: TxtToImgDto
    ): TxtToImgResult {
        return TxtToImgResult(
            id = dto.id,
            outputUrls = dto.output.map { it.replaceDomain() },
            status = dto.status,
            generationTime = dto.generationTime
        )
    }

    fun map(
        entity: ResultEntity
    ): TxtToImgResult {
        return TxtToImgResult(
            id = entity.id,
            outputUrls = entity.output.map { it.replaceDomain() },
            status = entity.status,
            generationTime = entity.generationTime
        )
    }

    fun mapMeta(
        entity: ResultMetaDataEntity
    ): TxtToImgResultMetaData {
        return TxtToImgResultMetaData(
            prompt = entity.prompt,
            modelId = entity.modelId,
            negativePrompt = entity.negativePrompt,
            w = entity.w,
            h = entity.h,
            guidanceScale = entity.guidanceScale,
            seed = entity.seed,
            steps = entity.steps,
            nSamples = entity.nSamples,
            fullUrl = entity.fullUrl,
            upscale = entity.upscale,
            multiLingual = entity.multiLingual,
            panorama = entity.panorama,
            selfAttention = entity.selfAttention,
            embeddings = entity.embeddings,
            lora = entity.lora,
            outdir = entity.outdir,
            filePrefix = entity.filePrefix,
        )
    }

    fun map(
        id: Int,
        outputUrls: List<String>,
        status: String,
        generationTime: Double? = null,
        request: TxtToImgRequestBody
    ): TxtToImgResult {
        return TxtToImgResult(
            id = id,
            outputUrls = outputUrls.map { it.replaceDomain() },
            status = status,
            generationTime = generationTime
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
