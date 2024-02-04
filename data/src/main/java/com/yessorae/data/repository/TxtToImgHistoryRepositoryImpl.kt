package com.yessorae.data.repository

import com.yessorae.common.Logger
import com.yessorae.data.util.replaceDomain
import com.yessorae.data.local.database.dao.TxtToImgHistoryDao
import com.yessorae.data.local.database.model.ResultEntity
import com.yessorae.data.local.database.model.TxtToImgHistoryEntity
import com.yessorae.data.local.database.model.asDomainModel
import com.yessorae.data.local.database.model.asEntity
import com.yessorae.data.local.database.model.asResultEntity
import com.yessorae.data.local.preference.PreferenceDataStore
import com.yessorae.data.remote.stablediffusion.api.TxtToImgApi
import com.yessorae.data.remote.stablediffusion.model.request.FetchQueuedImageRequestDto
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequestDto
import com.yessorae.data.remote.stablediffusion.model.request.asHistoryEntity
import com.yessorae.data.remote.stablediffusion.model.response.MetaDataResponseDto
import com.yessorae.data.remote.stablediffusion.model.response.TxtToImgDto
import com.yessorae.data.remote.stablediffusion.model.response.asEntity
import com.yessorae.data.util.handleResponse
import com.yessorae.domain.model.MetaDataResponse
import com.yessorae.domain.model.TxtToImgHistory
import com.yessorae.domain.model.TxtToImgRequest
import com.yessorae.domain.model.TxtToImgResult
import com.yessorae.domain.repository.TxtToImgHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class TxtToImgHistoryRepositoryImpl @Inject constructor(
    private val txtToImgApi: TxtToImgApi,
    private val txtToImgHistoryDao: TxtToImgHistoryDao,
    private val preferenceDataStore: PreferenceDataStore
) : TxtToImgHistoryRepository {
    override suspend fun insertRequestHistory(
        requestBody: TxtToImgHistory
    ): Long {
        Logger.data("TxtToImgHistoryRepository - insert - requestBody $requestBody")
        Logger.data("TxtToImgHistoryRepository - insert - requestBody.asHistoryEntity() ${requestBody.asEntity()}")
        val historyId = txtToImgHistoryDao.insert(
            entity = requestBody.asEntity()
        )
        preferenceDataStore.setLastTxtToImageRequestHistoryId(historyId = historyId)
        return historyId
    }

    override suspend fun updateRequestHistory(
        id: Int,
        result: TxtToImgResult,
        meta: MetaDataResponse
    ) {
        val oldHistory = txtToImgHistoryDao.getTxtToImgHistoryModel(id = id)
        Logger.data("TxtToImgHistoryRepository - update - oldHistory $oldHistory")
        val newHistory = oldHistory.copy(
            result = result.asEntity(),
            meta = meta.asEntity()
        )
        Logger.data("TxtToImgHistoryRepository - update - newHistory $newHistory")
        txtToImgHistoryDao.update(entity = newHistory)
    }

    override suspend fun getTxtToImgHistory(
        id: Int
    ): TxtToImgHistory {
        return txtToImgHistoryDao.getTxtToImgHistoryModel(id = id).asDomainModel()
    }

    override suspend fun getLastTxtToImgHistory(): TxtToImgHistory? {
        val historyId = preferenceDataStore.getLastTxtToImageRequestHistoryId()
        return historyId?.toInt()?.let { id ->
            getTxtToImgHistory(id = id)
        }
    }

    override fun getHistories(): Flow<List<TxtToImgHistory>> {
        return txtToImgHistoryDao.getTxtToImgHistoryModels().onEach {
            Logger.data("TxtToImgHistoryRepository - getHistories - $it")
        }
            .map { list ->
                list.filter { it.result != null }
                    .map { it.asDomainModel() }
            }
    }

    override suspend fun deleteHistory(
        id: Int
    ) {
        txtToImgHistoryDao.deleteById(id = id)
    }

    override suspend fun fetchQueuedImage(
        id: Int,
        requestId: String
    ): TxtToImgHistory {
        val oldEntity = txtToImgHistoryDao.getTxtToImgHistoryModel(id = id)
        val dto = txtToImgApi.fetchQueuedImage(
            FetchQueuedImageRequestDto(
                requestId = requestId
            )
        ).handleResponse()

        val newEntity = oldEntity.copy(
            result = oldEntity.result?.copy(
                status = dto.status,
                id = dto.id,
                output = dto.output.map { it.replaceDomain() }
            ) ?: ResultEntity(
                status = dto.status,
                id = dto.id,
                output = dto.output.map { it.replaceDomain() },
                generationTime = 0.0 // todo null 처리
            )
        )
        Logger.data("fetchQueuedImage dto $dto")
        Logger.data("fetchQueuedImage oldEntity $oldEntity")
        Logger.data("fetchQueuedImage newEntity $newEntity", error = true)

        txtToImgHistoryDao.update(newEntity)
        return newEntity.asDomainModel()
    }
}
