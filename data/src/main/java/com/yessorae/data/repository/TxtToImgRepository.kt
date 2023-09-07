package com.yessorae.data.repository

import androidx.paging.PagingSource
import com.yessorae.data.local.dao.PromptDao
import com.yessorae.data.local.model.PromptEntity
import com.yessorae.data.remote.api.ModelListApi
import com.yessorae.data.remote.api.TxtToImgApi
import com.yessorae.data.remote.model.request.FetchQueuedImageRequest
import com.yessorae.data.remote.model.request.TxtToImgRequest
import com.yessorae.data.remote.model.response.FetchQueuedImageDto
import com.yessorae.data.remote.model.response.PublicModelDto
import com.yessorae.data.remote.model.response.TxtToImgDto
import com.yessorae.data.util.handleResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TxtToImgRepository @Inject constructor(
    private val txtToImgApi: TxtToImgApi,
    private val modelListApi: ModelListApi,
    private val promptDao: PromptDao
) {
    suspend fun generateImage(
        request: TxtToImgRequest
    ): TxtToImgDto {
        return txtToImgApi.generateImage(request).handleResponse()
    }

    suspend fun fetchQueuedImage(
        requestId: String
    ): FetchQueuedImageDto {
        return txtToImgApi.fetchQueuedImage(
            FetchQueuedImageRequest(
                requestId = requestId
            )
        ).handleResponse()
    }

    suspend fun upscaleImage(

    ) {
        // todo url 필요해서 파베 연결이 먼저
    }

    suspend fun getPublicModels(usingCache: Boolean = true): PublicModelDto {
        return modelListApi.getPublicModels().handleResponse()
    }

    suspend fun getPositivePrompts(): List<PromptEntity> {
        return promptDao.getPromptsOrderedByCreatedAt(
            isPositive = true
        )
    }

    suspend fun getNegativePrompts(): List<PromptEntity> {
        return promptDao.getPromptsOrderedByCreatedAt(
            isPositive = false
        )
    }

    suspend fun insertPrompt(prompts: PromptEntity) {
        promptDao.insert(prompts)
    }
}
