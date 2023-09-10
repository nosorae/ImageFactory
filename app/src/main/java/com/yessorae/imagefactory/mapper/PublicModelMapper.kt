package com.yessorae.imagefactory.mapper

import com.yessorae.common.Constants
import com.yessorae.common.Logger
import com.yessorae.common.replacePubDomain
import com.yessorae.data.remote.stablediffusion.model.response.PublicModelDto
import com.yessorae.data.remote.stablediffusion.model.response.PublicModelItem
import com.yessorae.imagefactory.model.EmbeddingsModelOption
import com.yessorae.imagefactory.model.LoRaModelOption
import com.yessorae.imagefactory.model.SDModelOption
import com.yessorae.imagefactory.util.TextString
import javax.inject.Inject

class PublicModelMapper @Inject constructor() {
    fun mapSDModelOption(dto: PublicModelDto): List<SDModelOption> {
        return dto.filter { model ->
            isSDModel(model)
        }.mapIndexed { _, model ->
            SDModelOption(
                id = model.modelId,
                image = model.screenshots.replacePubDomain(),
                title = TextString(model.modelName),
                selected = false,
                generationCount = try {
                    model.apiCalls?.toLong()
                } catch (e: Exception) {
                    null
                }
            )
        }.sortedByDescending {
            it.generationCount
        }.mapIndexed { index, model ->
            if (index == 0) {
                model.copy(selected = true)
            } else {
                model
            }
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
                selected = false,
                generationCount = try {
                    model.apiCalls?.toLong()
                } catch (e: Exception) {
                    null
                }
            )
        }.sortedByDescending {
            it.generationCount
        }
    }

    fun mapEmbeddingsModelOption(dto: PublicModelDto): List<EmbeddingsModelOption> {
        return dto.filter { model ->
            isEmbeddingsModel(model)
        }.mapIndexed { index, model ->
            EmbeddingsModelOption(
                id = model.modelId,
                image = model.screenshots, // .replacePubDomain(),
                title = TextString(model.modelName),
                selected = false,
                generationCount = try {
                    model.apiCalls?.toLong()
                } catch (e: Exception) {
                    null
                }
            )
        }.sortedByDescending {
            it.generationCount
        }
    }

    fun printEtc(dto: PublicModelDto) {
        dto.forEach {
            if (isSDModel(it).not() && isLoRaModel(it).not() && isEmbeddingsModel(it).not() && isControlNetModel(
                    it
                ).not()
            ) {
                Logger.presentation(message = "etc: $it")
            }
        }
    }

    private fun isSDModel(dto: PublicModelItem): Boolean {
        return dto.modelCategory == Constants.ARG_MODEL_TYPE_STABLE_DIFFUSION ||
            dto.modelCategory == Constants.ARG_MODEL_TYPE_STABLE_DIFFUSION_XL
    }

    private fun isLoRaModel(dto: PublicModelItem): Boolean {
        return dto.modelCategory == Constants.ARG_MODEL_TYPE_LORA
    }

    private fun isEmbeddingsModel(dto: PublicModelItem): Boolean {
        return dto.modelCategory == Constants.ARG_MODEL_TYPE_EMBEDDINGS
    }

    private fun isControlNetModel(dto: PublicModelItem): Boolean {
        return dto.modelCategory == Constants.ARG_MODEL_TYPE_CONTROL_NET
    }
}
