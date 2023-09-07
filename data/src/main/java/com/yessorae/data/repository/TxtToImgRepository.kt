package com.yessorae.data.repository

import android.graphics.Bitmap
import androidx.paging.PagingSource
import com.yessorae.common.Logger
import com.yessorae.data.local.dao.PromptDao
import com.yessorae.data.local.model.PromptEntity
import com.yessorae.data.remote.firebase.FireStorageService
import com.yessorae.data.remote.firebase.model.ImageUploadResponse
import com.yessorae.data.remote.stablediffusion.api.ModelListApi
import com.yessorae.data.remote.stablediffusion.api.TxtToImgApi
import com.yessorae.data.remote.stablediffusion.model.request.FetchQueuedImageRequest
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequest
import com.yessorae.data.remote.stablediffusion.model.response.FetchQueuedImageDto
import com.yessorae.data.remote.stablediffusion.model.response.PublicModelDto
import com.yessorae.data.remote.stablediffusion.model.response.TxtToImgDto
import com.yessorae.data.util.handleResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TxtToImgRepository @Inject constructor(
    private val txtToImgApi: TxtToImgApi,
    private val modelListApi: ModelListApi,
    private val promptDao: PromptDao,
    private val firebaseStorageService: FireStorageService
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

    suspend fun uploadImage(
        bitmap: Bitmap,
        path: String,
        name: String
    ): Flow<ImageUploadResponse> {
        return firebaseStorageService.uploadImage(
            bitmap = bitmap,
            path = path,
            name = name
        )
    }

    @OptIn(FlowPreview::class)
    suspend fun upscaleImage(
        bitmap: Bitmap,
        path: String,
        name: String
    ) {
        uploadImage(
            bitmap = bitmap,
            path = path,
            name = name
        ).collectLatest {
            Logger.data("${it.uri}")
        }
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
