package com.yessorae.data.repository

import com.yessorae.data.local.database.dao.PublicModelDao
import com.yessorae.data.local.database.model.PublicModelEntity
import com.yessorae.data.local.database.model.asDomainModel
import com.yessorae.data.remote.firebase.config.FirebaseRemoteConfigDataSource
import com.yessorae.data.remote.firebase.config.model.Model
import com.yessorae.data.remote.firebase.config.model.asEmbeddingsModel
import com.yessorae.data.remote.firebase.config.model.asLoRaModel
import com.yessorae.data.remote.firebase.config.model.asSDModel
import com.yessorae.data.remote.stablediffusion.api.ModelListApi
import com.yessorae.data.remote.stablediffusion.model.response.mapToEntity
import com.yessorae.data.util.handleResponse
import com.yessorae.domain.model.PublicModel
import com.yessorae.domain.model.parameter.EmbeddingsModel
import com.yessorae.domain.model.parameter.LoRaModel
import com.yessorae.domain.model.parameter.SDModel
import com.yessorae.domain.repository.PublicModelRepository
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PublicModelRepositoryImpl @Inject constructor(
    private val publicModelDao: PublicModelDao,
    private val modelListApi: ModelListApi,
    private val preferenceRepositoryImpl: PreferenceRepositoryImpl,
    private val firebaseRemoteConfigDataSource: FirebaseRemoteConfigDataSource
    ) : PublicModelRepository {
    override suspend fun getSyncedAllPublicModels(): List<PublicModel> {
        val oldEntities = publicModelDao.getPublicModelByCallCounts()
        val lastUpdateTime = preferenceRepositoryImpl.getLastModelUpdateTime()

        return if (oldEntities.isEmpty() || lastUpdateTime?.isDaysApartFromNow(day = 3) != false) {
            val remoteData = modelListApi.getPublicModels().handleResponse()
            val newEntities = remoteData.map {
                it.mapToEntity()
            }

            publicModelDao.insertAll(newEntities)

            preferenceRepositoryImpl.setLastModelUpdateTime()

            newEntities.map(PublicModelEntity::asDomainModel)
        } else {
            oldEntities.map(PublicModelEntity::asDomainModel)
        }
    }

    override suspend fun getFeaturedModelsOfStableDiffusion(): List<SDModel> {
        return firebaseRemoteConfigDataSource.getFeaturedModelsOfCheckPoints().map(Model::asSDModel)
    }

    override suspend fun getFeaturedModelsOfLoRa(): List<LoRaModel> {
        return firebaseRemoteConfigDataSource.getFeaturedModelsOfLoRa().map(Model::asLoRaModel)
    }

    override suspend fun getFeaturedModelsOfEmbeddings(): List<EmbeddingsModel> {
        return firebaseRemoteConfigDataSource.getFeaturedModelsOfEmbeddings().map(Model::asEmbeddingsModel)
    }

    companion object {
        private fun LocalDateTime.isDaysApartFromNow(
            day: Int
        ): Boolean {
            val now = LocalDateTime.now()
            val daysApart = ChronoUnit.DAYS.between(this, now)
            return Math.abs(daysApart) >= day
        }
    }
}

