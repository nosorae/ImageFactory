package com.yessorae.imagefactory.ui.screen.tti

import com.yessorae.common.Logger
import com.yessorae.common.replacePubDomain
import com.yessorae.data.remote.model.response.PublicModelDto
import com.yessorae.data.remote.model.response.PublicModelItem
import com.yessorae.imagefactory.model.EmbeddingsModelOption
import com.yessorae.imagefactory.model.LoRaModelOption
import com.yessorae.imagefactory.model.SDModelOption
import com.yessorae.imagefactory.ui.util.TextString
import javax.inject.Inject

class PublicModelMapper @Inject constructor() {
    fun mapSDModelOption(dto: PublicModelDto): List<SDModelOption> {
        return dto.filter { model ->
            isSDModel(model)
        }.mapIndexed { index, model ->
            Logger.presentation(message = model.screenshots)
//            Logger.presentation(message = model.screenshots.replaceDomain(), error =true)
            SDModelOption(
                id = model.modelId,
                image = model.screenshots.replacePubDomain(),
                title = TextString(model.modelName),
                selected = index == 0
            )
        }
    }

    fun mapLoRaModelOption(dto: PublicModelDto): List<LoRaModelOption> {
        return dto.filter { model ->
            isLoRaModel(model)
        }.mapIndexed { index, model ->
            LoRaModelOption(
                id = model.modelId,
                image = model.screenshots.replacePubDomain(),
                title = TextString(model.modelName),
                selected = index == 0
            )
        }
    }

    fun mapEmbeddingsModelOption(dto: PublicModelDto): List<EmbeddingsModelOption> {
        return dto.filter { model ->
            isEmbeddingModel(model)
        }.mapIndexed { index, model ->
            EmbeddingsModelOption(
                id = model.modelId,
                image = model.screenshots.replacePubDomain(),
                title = TextString(model.modelName),
                selected = index == 0
            )
        }
    }

    private fun isSDModel(dto: PublicModelItem): Boolean {
        return dto.description.contains("stable", ignoreCase = true) ||
            dto.description.contains("diffusion", ignoreCase = true)
    }

    private fun isLoRaModel(dto: PublicModelItem): Boolean {
        return dto.description.contains("lora", ignoreCase = true)
    }

    private fun isEmbeddingModel(dto: PublicModelItem): Boolean {
        return dto.description.contains("embedding", ignoreCase = true)
    }

    private fun isControlNetModel(dto: PublicModelItem): Boolean {
        return dto.description.contains("control", ignoreCase = true) ||
            dto.modelId.contains("net", ignoreCase = true)
    }
}
