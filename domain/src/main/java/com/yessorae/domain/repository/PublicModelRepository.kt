package com.yessorae.domain.repository

import com.yessorae.domain.model.PublicModel

interface PublicModelRepository {
    suspend fun getPublicModels(): List<PublicModel>
}