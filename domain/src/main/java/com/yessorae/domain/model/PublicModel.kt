package com.yessorae.domain.model

import com.yessorae.domain.model.parameter.EmbeddingsModel
import com.yessorae.domain.model.parameter.LoRaModel
import com.yessorae.domain.model.parameter.SDModel
import com.yessorae.domain.util.StableDiffusionConstants

data class PublicModel(
    val modelId: String, // midjourney
    val status: String, // model_ready
    val createdAt: String?, // null
    val instancePrompt: String?, // mdjrny-v4 style
    val apiCalls: Long?, // "1690106"
    val modelCategory: String, // stable_diffusion
    val isNsfw: Boolean?, // yes || no
    val featured: Boolean?, // yes || no
    val modelName: String, // MidJourney V4
    val description: String?, // Openjourney is an open source Stable Diffusion fine tuned model on Midjourney images, by PromptHero
    val screenshots: String, // https://d1okzptojspljx.cloudfront.net/generations/14853540911669470514.png
    val serverSync: Boolean = false
)

fun List<PublicModel>.asSDModels(): List<SDModel> {
    return filter { publicModel ->
        publicModel.modelCategory == StableDiffusionConstants.ARG_MODEL_TYPE_STABLE_DIFFUSION ||
            publicModel.modelCategory == StableDiffusionConstants.ARG_MODEL_TYPE_STABLE_DIFFUSION_XL
    }.map { publicModel ->
        SDModel(
            id = publicModel.modelId,
            imgUrl = publicModel.screenshots,
            displayName = publicModel.modelName
        )
    }
}

fun List<PublicModel>.asLoRaModels(): List<LoRaModel> {
    return filter { publicModel ->
        publicModel.modelCategory == StableDiffusionConstants.ARG_MODEL_TYPE_LORA
    }.map { publicModel ->
        LoRaModel(
            id = publicModel.modelId,
            imgUrl = publicModel.screenshots,
            displayName = publicModel.modelName
        )
    }
}

fun List<PublicModel>.asEmbeddingsModels(): List<EmbeddingsModel> {
    return filter { publicModel ->
        publicModel.modelCategory == StableDiffusionConstants.ARG_MODEL_TYPE_LORA
    }.map { publicModel ->
        EmbeddingsModel(
            id = publicModel.modelId,
            imgUrl = publicModel.screenshots,
            displayName = publicModel.modelName
        )
    }
}