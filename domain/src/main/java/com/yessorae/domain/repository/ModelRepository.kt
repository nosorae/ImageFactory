package com.yessorae.domain.repository

import com.yessorae.domain.model.PublicModel
import com.yessorae.domain.model.parameter.EmbeddingsModel
import com.yessorae.domain.model.parameter.LoRaModel
import com.yessorae.domain.model.parameter.SDModel
import kotlinx.coroutines.flow.Flow

interface ModelRepository {
    suspend fun getSyncedAllPublicModels(): List<PublicModel>

    suspend fun getFeaturedSDModels(): List<SDModel>

    suspend fun getFeaturedLoRaModels(): List<LoRaModel>

    suspend fun getFeaturedEmbeddingsModels(): List<EmbeddingsModel>

    fun getRecentlyUsedSDModels(): Flow<List<SDModel>>

    fun getRecentlyUsedLoRaModels(): Flow<List<LoRaModel>>

    fun getRecentlyUsedEmbeddingsModels(): Flow<List<EmbeddingsModel>>
}