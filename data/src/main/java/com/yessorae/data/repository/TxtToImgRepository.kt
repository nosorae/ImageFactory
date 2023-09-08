package com.yessorae.data.repository

import android.graphics.Bitmap
import com.yessorae.data.BuildConfig
import com.yessorae.data.local.database.dao.PromptDao
import com.yessorae.data.local.database.model.PromptEntity
import com.yessorae.data.local.preference.PreferenceService
import com.yessorae.data.remote.firebase.FireStorageService
import com.yessorae.data.remote.firebase.model.ImageUploadResponse
import com.yessorae.data.remote.stablediffusion.api.ImageEditingApi
import com.yessorae.data.remote.stablediffusion.api.ModelListApi
import com.yessorae.data.remote.stablediffusion.api.TxtToImgApi
import com.yessorae.data.remote.stablediffusion.model.request.FetchQueuedImageRequest
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequest
import com.yessorae.data.remote.stablediffusion.model.request.UpscaleRequest
import com.yessorae.data.remote.stablediffusion.model.response.FetchQueuedImageDto
import com.yessorae.data.remote.stablediffusion.model.response.PublicModelDto
import com.yessorae.data.remote.stablediffusion.model.response.TxtToImgDto
import com.yessorae.data.remote.stablediffusion.model.response.UpscaleDto
import com.yessorae.data.util.ImageFactoryException
import com.yessorae.data.util.handleResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TxtToImgRepository @Inject constructor(
    private val txtToImgApi: TxtToImgApi,
    private val modelListApi: ModelListApi,
    private val imageEditingApi: ImageEditingApi,
    private val promptDao: PromptDao,
    private val firebaseStorageService: FireStorageService,
    private val preferenceService: PreferenceService
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

    private suspend fun setLastRequest(request: TxtToImgRequest) {
        preferenceService.setLastTxtToImageRequest(request = request)
    }

    fun getLastRequest(): Flow<TxtToImgRequest?> {
        return preferenceService.getLastTxtToImageRequest()
    }

    suspend fun upscaleImage(
        bitmap: Bitmap,
        path: String,
        name: String,
        scale: Int,
        faceEnhance: Boolean
    ): UpscaleDto {
        val url = uploadImage(
            bitmap = bitmap,
            path = path,
            name = name
        ).firstOrNull()?.uri?.toString()
            ?: throw ImageFactoryException.FirebaseStorageException("downloadUrl is null")

        return imageEditingApi.upscaleImage(
            UpscaleRequest(
                key = BuildConfig.STABLE_DIFFUSION_API_API_KEY,
                url = url,
                scale = scale,
                faceEnhance = faceEnhance
            )
        ).handleResponse()
    }

    private fun uploadImage(
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
