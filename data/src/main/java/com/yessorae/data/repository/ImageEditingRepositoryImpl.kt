package com.yessorae.data.repository

import com.yessorae.data.BuildConfig
import com.yessorae.data.remote.stablediffusion.api.ImageEditingApi
import com.yessorae.data.remote.stablediffusion.model.request.UpscaleRequestDto
import com.yessorae.data.remote.stablediffusion.model.response.asDomainModel
import com.yessorae.data.util.handleResponse
import com.yessorae.domain.model.UpscaleResult
import com.yessorae.domain.repository.ImageEditingRepository
import javax.inject.Inject

class ImageEditingRepositoryImpl @Inject constructor(
    private val imageEditingApi: ImageEditingApi
) : ImageEditingRepository {
    override suspend fun upscaleImage(
        url: String,
        scale: Int,
        faceEnhance: Boolean
    ): UpscaleResult {
        return imageEditingApi
            .upscaleImage(
                UpscaleRequestDto(
                    key = BuildConfig.STABLE_DIFFUSION_API_API_KEY,
                    url = url,
                    scale = scale,
                    faceEnhance = faceEnhance
                )
            )
            .handleResponse()
            .asDomainModel()
    }
}
