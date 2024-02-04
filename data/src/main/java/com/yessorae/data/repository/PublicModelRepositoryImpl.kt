package com.yessorae.data.repository

import com.yessorae.data.local.database.dao.PublicModelDao
import com.yessorae.data.local.database.model.PublicModelEntity
import com.yessorae.data.local.database.model.asDomainModel
import com.yessorae.data.local.preference.PreferenceDataStore
import com.yessorae.data.remote.stablediffusion.api.ModelListApi
import com.yessorae.data.remote.stablediffusion.model.response.mapToEntity
import com.yessorae.data.util.handleResponse
import com.yessorae.domain.model.PublicModel
import com.yessorae.domain.repository.PublicModelRepository
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PublicModelRepositoryImpl @Inject constructor(
    private val publicModelDao: PublicModelDao,
    private val modelListApi: ModelListApi,
    private val preferenceDataStore: PreferenceDataStore
) : PublicModelRepository {
    override suspend fun getPublicModels(): List<PublicModel> {
        val oldEntities = publicModelDao.getPublicModelByCallCounts()
        val lastUpdateTime = preferenceDataStore.getLastModelUpdateTime()

        return if (oldEntities.isEmpty() || lastUpdateTime?.isDaysApartFromNow(day = 3) != false) {
            val remoteData = modelListApi.getPublicModels().handleResponse()
            val newEntities = remoteData.map {
                it.mapToEntity()
            }

            publicModelDao.insertAll(newEntities)

            preferenceDataStore.setLastModelUpdateTime()

            newEntities.map(PublicModelEntity::asDomainModel)
        } else {
            oldEntities.map(PublicModelEntity::asDomainModel)
        }
    }

    private fun LocalDateTime.isDaysApartFromNow(
        day: Int
    ): Boolean {
        val now = LocalDateTime.now()
        val daysApart = ChronoUnit.DAYS.between(this, now)
        return Math.abs(daysApart) >= day
    }
}

