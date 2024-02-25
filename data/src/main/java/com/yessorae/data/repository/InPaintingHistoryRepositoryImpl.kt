package com.yessorae.data.repository

import com.yessorae.data.local.database.dao.InPaintingHistoryDao
import com.yessorae.data.local.database.model.inpainting.InPaintingHistoryEntity
import com.yessorae.data.local.database.model.inpainting.asDomainModel
import com.yessorae.data.local.database.model.inpainting.asEntity
import com.yessorae.domain.model.inpainting.InPaintingHistory
import com.yessorae.domain.model.inpainting.InPaintingRequest
import com.yessorae.domain.repository.inpainting.InPaintingHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InPaintingHistoryRepositoryImpl @Inject constructor(
    private val inPaintingHistoryDao: InPaintingHistoryDao,
) : InPaintingHistoryRepository {
    override suspend fun insertRequestHistory(
        requestBody: InPaintingRequest
    ): Long {
        return inPaintingHistoryDao.insert(
            entity = InPaintingHistoryEntity(
                request = requestBody.asEntity()
            )
        )
    }

    override suspend fun getHistory(
        id: Int
    ): InPaintingHistory {
        return inPaintingHistoryDao
            .getHistoryModel(id = id)
            .asDomainModel()
    }

    override fun getHistories(): Flow<List<InPaintingHistory>> {
        return inPaintingHistoryDao
            .getHistoryModels()
            .map { list ->
                list.map(InPaintingHistoryEntity::asDomainModel)
            }
    }

    override suspend fun updateHistory(history: InPaintingHistory) {
        inPaintingHistoryDao.update(history.asEntity())
    }

    override suspend fun deleteHistory(id: Int) {
        inPaintingHistoryDao.deleteById(id = id)
    }
}

