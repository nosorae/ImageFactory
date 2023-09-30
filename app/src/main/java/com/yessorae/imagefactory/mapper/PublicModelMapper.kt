package com.yessorae.imagefactory.mapper

import com.yessorae.common.Constants
import com.yessorae.common.replacePubDomain
import com.yessorae.data.local.database.model.PublicModelEntity
import com.yessorae.imagefactory.model.EmbeddingsModelOption
import com.yessorae.imagefactory.model.LoRaModelOption
import com.yessorae.imagefactory.model.LoRaModelOption.Companion.DEFAULT_STRENGTH
import com.yessorae.imagefactory.model.SDModelOption
import com.yessorae.imagefactory.util.TextString
import javax.inject.Inject

class PublicModelMapper @Inject constructor() {
    fun mapSDModelOption(dto: List<PublicModelEntity>, lastModelId: String?): List<SDModelOption> {
        return dto.filter { model ->
            isSDModel(model)
        }.mapIndexed { index, model ->
            SDModelOption(
                id = model.modelId,
                image = model.screenshots.replacePubDomain(),
                title = TextString(model.modelName),
                selected = if (lastModelId != null) {
                    lastModelId == model.modelId
                } else {
                    index == 0
                },
                generationCount = model.apiCalls
            )
        }
    }

    fun mapLoRaModelOption(
        dto: List<PublicModelEntity>,
        lastIds: List<String>?,
        lastStrength: List<Float>?
    ): List<LoRaModelOption> {
        val lastModelSet = lastIds?.toSet()
        return dto.filter { model ->
            isLoRaModel(model)
        }.mapIndexed { _, model ->
            val contain = lastModelSet?.contains(model.modelId) ?: false
            LoRaModelOption(
                id = model.modelId,
                image = model.screenshots.replacePubDomain(),
                title = TextString(model.modelName),
                selected = contain,
                generationCount = model.apiCalls,
                strength = if (contain) {
                    lastIds?.indexOf(model.modelId)?.let { strengthIndex ->
                        lastStrength?.getOrNull(strengthIndex)
                            ?: DEFAULT_STRENGTH
                    } ?: DEFAULT_STRENGTH
                } else {
                    DEFAULT_STRENGTH
                }
            )
        }
    }

    fun mapEmbeddingsModelOption(
        dto: List<PublicModelEntity>,
        lastIds: List<String>?
    ): List<EmbeddingsModelOption> {
        val lastModelSet = lastIds?.toSet()
        return dto.filter { model ->
            isEmbeddingsModel(model)
        }.mapIndexed { _, model ->
            EmbeddingsModelOption(
                id = model.modelId,
                image = model.screenshots.replacePubDomain(),
                title = TextString(model.modelName),
                selected = lastModelSet?.contains(model.modelId) ?: false,
                generationCount = model.apiCalls
            )
        }
    }

    private fun isSDModel(dto: PublicModelEntity): Boolean {
        return dto.modelCategory == Constants.ARG_MODEL_TYPE_STABLE_DIFFUSION ||
            dto.modelCategory == Constants.ARG_MODEL_TYPE_STABLE_DIFFUSION_XL
    }

    private fun isLoRaModel(dto: PublicModelEntity): Boolean {
        return dto.modelCategory == Constants.ARG_MODEL_TYPE_LORA
    }

    private fun isEmbeddingsModel(dto: PublicModelEntity): Boolean {
        return dto.modelCategory == Constants.ARG_MODEL_TYPE_EMBEDDINGS
    }

    private fun isControlNetModel(dto: PublicModelEntity): Boolean {
        return dto.modelCategory == Constants.ARG_MODEL_TYPE_CONTROL_NET
    }
}
