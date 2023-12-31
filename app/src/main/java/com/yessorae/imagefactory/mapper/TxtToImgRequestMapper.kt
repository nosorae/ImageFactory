package com.yessorae.imagefactory.mapper

import com.yessorae.data.local.database.model.RequestBodyEntity
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequestBody
import com.yessorae.imagefactory.ui.model.TxtToImgRequest
import javax.inject.Inject

class TxtToImgRequestMapper @Inject constructor() {
    fun map(requestBody: TxtToImgRequestBody): TxtToImgRequest {
        return TxtToImgRequest(
            prompt = requestBody.prompt,
            modelId = requestBody.modelId,
            negativePrompt = requestBody.negativePrompt,
            width = requestBody.width,
            height = requestBody.height,
            guidanceScale = requestBody.guidanceScale,
            seed = requestBody.seed?.toLongOrNull(),
            steps = requestBody.numInferenceSteps,
            nSamples = requestBody.samples,
            upscale = requestBody.upscale,
            multiLingual = requestBody.multiLingual,
            panorama = requestBody.panorama,
            selfAttention = requestBody.selfAttention,
            embeddings = requestBody.embeddingsModel,
            lora = requestBody.loraModel,
            loraStrength = requestBody.loraStrength,
            scheduler = requestBody.scheduler,
            safetyChecker = requestBody.safetyChecker,
            enhancePrompt = requestBody.enhancePrompt
        )
    }

    fun map(entity: RequestBodyEntity): TxtToImgRequest {
        return TxtToImgRequest(
            prompt = entity.prompt,
            modelId = entity.modelId,
            negativePrompt = entity.negativePrompt,
            width = entity.width,
            height = entity.height,
            guidanceScale = entity.guidanceScale,
            seed = entity.seed?.toLongOrNull(),
            steps = entity.numInferenceSteps,
            nSamples = entity.samples,
            upscale = entity.upscale,
            multiLingual = entity.multiLingual,
            panorama = entity.panorama,
            selfAttention = entity.selfAttention,
            embeddings = entity.embeddingsModel,
            lora = entity.loraModel,
            loraStrength = entity.loraStrength,
            scheduler = entity.scheduler,
            safetyChecker = entity.safetyChecker,
            enhancePrompt = entity.enhancePrompt
        )
    }

    fun mapToRequestBody(request: TxtToImgRequest): TxtToImgRequestBody {
        return TxtToImgRequestBody(
            prompt = request.prompt,
            modelId = request.modelId,
            negativePrompt = request.negativePrompt,
            width = request.width,
            height = request.height,
            guidanceScale = request.guidanceScale,
            seed = request.seed?.toString(),
            numInferenceSteps = request.steps,
            samples = request.nSamples,
            upscale = request.upscale,
            multiLingual = request.multiLingual,
            panorama = request.panorama,
            selfAttention = request.selfAttention,
            embeddingsModel = request.embeddings,
            loraModel = request.lora,
            loraStrength = request.loraStrength,
            scheduler = request.scheduler,
            safetyChecker = request.safetyChecker,
            enhancePrompt = request.enhancePrompt
        )
    }
}
