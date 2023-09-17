package com.yessorae.data.repository

import com.yessorae.common.Logger
import com.yessorae.data.local.database.dao.TxtToImgHistoryDao
import com.yessorae.data.local.database.model.TxtToImgHistoryEntity
import com.yessorae.data.local.database.model.asEntity
import com.yessorae.data.local.database.model.asHistoryEntity
import com.yessorae.data.local.database.model.asRequestBody
import com.yessorae.data.local.database.model.asResultEntity
import com.yessorae.data.remote.stablediffusion.api.TxtToImgApi
import com.yessorae.data.remote.stablediffusion.model.request.FetchQueuedImageRequestBody
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequestBody
import com.yessorae.data.remote.stablediffusion.model.response.FetchQueuedImageDto
import com.yessorae.data.remote.stablediffusion.model.response.MetaDataDto
import com.yessorae.data.remote.stablediffusion.model.response.TxtToImgDto
import com.yessorae.data.util.handleResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class TxtToImgHistoryRepository @Inject constructor(
    private val txtToImgApi: TxtToImgApi,
    private val txtToImgHistoryDao: TxtToImgHistoryDao
) {
    suspend fun insertRequestHistory(
        requestBody: TxtToImgRequestBody
    ): Long {
        Logger.data("TxtToImgHistoryRepository - insert - requestBody $requestBody")
        Logger.data("TxtToImgHistoryRepository - insert - requestBody.asHistoryEntity() ${requestBody.asHistoryEntity()}")
        return txtToImgHistoryDao.insert(
            entity = requestBody.asHistoryEntity()
        )
    }

    suspend fun updateRequestHistory(
        id: Int,
        result: TxtToImgDto,
        meta: MetaDataDto
    ) {
        val oldHistory = txtToImgHistoryDao.getTxtToImgHistoryModel(id = id)
        Logger.data("TxtToImgHistoryRepository - update - oldHistory $oldHistory")
        val newHistory = oldHistory.copy(
            result = result.asResultEntity(),
            meta = meta.asEntity()
        )
        Logger.data("TxtToImgHistoryRepository - update - newHistory $newHistory")
        txtToImgHistoryDao.update(entity = newHistory)
    }

    suspend fun getRequestHistory(
        requestId: Int
    ): TxtToImgRequestBody {
        return txtToImgHistoryDao.getTxtToImgHistoryModel(id = requestId).request.asRequestBody()
    }

    fun getHistories(): Flow<List<TxtToImgHistoryEntity>> {
        return txtToImgHistoryDao.getTxtToImgHistoryModels().onEach {
            Logger.data("TxtToImgHistoryRepository - getHistories - $it")
        }
            .map { list ->
                list.filter { it.result != null }
            }
    }

    suspend fun deleteHistory(
        requestId: Int
    ) {
        txtToImgHistoryDao.deleteById(id = requestId)
    }

    suspend fun fetchQueuedImage(
        requestId: String
    ): FetchQueuedImageDto {
        return txtToImgApi.fetchQueuedImage(
            FetchQueuedImageRequestBody(
                requestId = requestId
            )
        ).handleResponse()
    }

}