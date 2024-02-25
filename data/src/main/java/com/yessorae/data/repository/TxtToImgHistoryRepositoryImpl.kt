package com.yessorae.data.repository

import com.yessorae.common.Logger
import com.yessorae.data.local.database.dao.TxtToImgHistoryDao
import com.yessorae.data.local.database.model.tti.asDomainModel
import com.yessorae.data.local.database.model.tti.asEntity
import com.yessorae.domain.model.tti.TxtToImgHistory
import com.yessorae.domain.repository.tti.TxtToImgHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class TxtToImgHistoryRepositoryImpl @Inject constructor(
    private val txtToImgHistoryDao: TxtToImgHistoryDao,
) : TxtToImgHistoryRepository {
    override suspend fun insertRequestHistory(
        requestBody: TxtToImgHistory
    ): Long {
        Logger.temp("TxtToImgHistoryRepositoryImpl 요청 : ${requestBody.asEntity()}")
        return txtToImgHistoryDao.insert(
            entity = requestBody.asEntity()
        )
    }

    override suspend fun getHistory(
        id: Int
    ): TxtToImgHistory {
        return txtToImgHistoryDao.getHistoryModel(id = id).asDomainModel()
    }

    override fun getHistories(): Flow<List<TxtToImgHistory>> {
        return txtToImgHistoryDao.getHistoryModels().onEach {
            Logger.data("TxtToImgHistoryRepository - getHistories - $it")
        }
            .map { list ->
                list.map { it.asDomainModel() }
            }
    }

    override suspend fun updateHistory(history: TxtToImgHistory) {
        txtToImgHistoryDao.update(history.asEntity())
    }

    override suspend fun deleteHistory(
        id: Int
    ) {
        txtToImgHistoryDao.deleteById(id = id)
    }
}
