package com.yessorae.data.repository

import com.yessorae.common.Logger
import com.yessorae.data.local.database.dao.EmbeddingsModelDao
import com.yessorae.data.local.database.dao.LoRaModelDao
import com.yessorae.data.local.database.dao.PublicModelDao
import com.yessorae.data.local.database.dao.SDModelDao
import com.yessorae.data.local.database.model.EmbeddingsModelEntity
import com.yessorae.data.local.database.model.LoRaModelEntity
import com.yessorae.data.local.database.model.PublicModelEntity
import com.yessorae.data.local.database.model.SDModelEntity
import com.yessorae.data.local.database.model.asDomainModel
import com.yessorae.data.remote.firebase.config.FirebaseRemoteConfigDataSource
import com.yessorae.data.remote.firebase.config.model.Model
import com.yessorae.data.remote.firebase.config.model.asEmbeddingsModel
import com.yessorae.data.remote.firebase.config.model.asLoRaModel
import com.yessorae.data.remote.firebase.config.model.asSDModel
import com.yessorae.data.remote.stablediffusion.api.ModelListApi
import com.yessorae.data.remote.stablediffusion.model.response.asEntity
import com.yessorae.data.util.handleResponse
import com.yessorae.domain.model.PublicModel
import com.yessorae.domain.model.parameter.EmbeddingsModel
import com.yessorae.domain.model.parameter.LoRaModel
import com.yessorae.domain.model.parameter.SDModel
import com.yessorae.domain.repository.ModelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelRepositoryImpl @Inject constructor(
    private val publicModelDao: PublicModelDao,
    private val sdModelDao: SDModelDao,
    private val loRaModelDao: LoRaModelDao,
    private val embeddingsModelDao: EmbeddingsModelDao,
    private val modelListApi: ModelListApi,
    private val preferenceRepositoryImpl: PreferenceRepositoryImpl,
    private val firebaseRemoteConfigDataSource: FirebaseRemoteConfigDataSource
) : ModelRepository {
//    private val _allModelsFlow = MutableStateFlow<List<PublicModel>>(listOf())
//    val allModelsFlow: StateFlow<List<PublicModel>> = _allModelsFlow.asStateFlow().onSubscription {
//        // TODO:: SR-N getSyncedAllPublicModels 를 private으로 변경
//        _allModelsFlow.value = getSyncedAllPublicModels()
//    }

    private var cachedPublicModel: List<PublicModelEntity>? = null

    override suspend fun getSyncedAllPublicModels(): List<PublicModel> {
        val oldEntities =
            if (cachedPublicModel.isNullOrEmpty()) {
                publicModelDao.getPublicModelByCallCounts()
            } else {
                cachedPublicModel ?: listOf()
            }
        val lastUpdateTime = preferenceRepositoryImpl.getLastModelUpdateTime()

        return if (oldEntities.isEmpty() || lastUpdateTime?.isDaysApartFromNow(day = 3) != false) {
            val remoteData = modelListApi.getPublicModels().handleResponse()
//            Logger.data("$remoteData")
            val newEntities = remoteData.map {
                it.asEntity()
            }

            publicModelDao.insertAll(newEntities)

            preferenceRepositoryImpl.setLastModelUpdateTime()

            newEntities.map(PublicModelEntity::asDomainModel)
        } else {
            oldEntities.map(PublicModelEntity::asDomainModel)
        }
    }

    override suspend fun getFeaturedSDModels(): List<SDModel> {
        return firebaseRemoteConfigDataSource.getFeaturedModelsOfCheckPoints().map(Model::asSDModel)
    }

    override suspend fun getFeaturedLoRaModels(): List<LoRaModel> {
        return firebaseRemoteConfigDataSource.getFeaturedModelsOfLoRa().map(Model::asLoRaModel)
    }

    override suspend fun getFeaturedEmbeddingsModels(): List<EmbeddingsModel> {
        return firebaseRemoteConfigDataSource.getFeaturedModelsOfEmbeddings()
            .map(Model::asEmbeddingsModel)
    }

    override fun getRecentlyUsedSDModels(): Flow<List<SDModel>> {
        return sdModelDao.getSDModelsByUsedAt().map { it.map(SDModelEntity::asDomainModel) }
    }

    override fun getRecentlyUsedLoRaModels(): Flow<List<LoRaModel>> {
        return loRaModelDao.getLoRaModelsByUsedAt().map { it.map(LoRaModelEntity::asDomainModel) }
    }

    override fun getRecentlyUsedEmbeddingsModels(): Flow<List<EmbeddingsModel>> {
        return embeddingsModelDao.getEmbeddingsModelsByCallCounts()
            .map { it.map(EmbeddingsModelEntity::asDomainModel) }
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

