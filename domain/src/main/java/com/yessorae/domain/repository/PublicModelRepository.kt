package com.yessorae.domain.repository

import com.yessorae.domain.model.PublicModel
import com.yessorae.domain.model.parameter.EmbeddingsModel
import com.yessorae.domain.model.parameter.LoRaModel
import com.yessorae.domain.model.parameter.SDModel

interface PublicModelRepository {
    suspend fun getSyncedAllPublicModels(): List<PublicModel>

    suspend fun getFeaturedModelsOfStableDiffusion(): List<SDModel>

    suspend fun getFeaturedModelsOfLoRa(): List<LoRaModel>

    suspend fun getFeaturedModelsOfEmbeddings(): List<EmbeddingsModel>
}