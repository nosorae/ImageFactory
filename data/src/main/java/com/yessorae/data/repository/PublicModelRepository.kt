package com.yessorae.data.repository

import com.yessorae.data.local.database.dao.PublicModelDao
import com.yessorae.data.local.database.model.PublicModelEntity
import com.yessorae.data.local.database.model.mapToEntity
import com.yessorae.data.local.preference.PreferenceService
import com.yessorae.data.remote.stablediffusion.api.ModelListApi
import com.yessorae.data.util.handleResponse
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PublicModelRepository @Inject constructor(
    private val publicModelDao: PublicModelDao,
    private val modelListApi: ModelListApi,
    private val preferenceService: PreferenceService
) {
    suspend fun getPublicModels(): List<PublicModelEntity> {
        val oldEntities = publicModelDao.getPublicModelByCallCounts()
        val lastUpdateTime = preferenceService.getLastModelUpdateTime()


        return if (oldEntities.isEmpty() || lastUpdateTime?.isDaysApartFromNow(day = 7) != false) {
            val remoteData = modelListApi.getPublicModels().handleResponse()
            val newEntities = remoteData.map {
                it.mapToEntity()
            }

            publicModelDao.insertAll(newEntities)

            newEntities
        } else {
            oldEntities
        }
    }

    fun LocalDateTime.isDaysApartFromNow(
        day: Int
    ): Boolean {
        val now = LocalDateTime.now()
        val daysApart = ChronoUnit.DAYS.between(this, now)
        return Math.abs(daysApart) >= day
    }

}