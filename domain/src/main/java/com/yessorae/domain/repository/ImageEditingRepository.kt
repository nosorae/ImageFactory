package com.yessorae.domain.repository

import com.yessorae.domain.model.UpscaleResult

@Deprecated("API 결재 중단")
interface ImageEditingRepository {
    suspend fun upscaleImage(
        url: String,
        scale: Int = 4,
        faceEnhance: Boolean = true
    ): UpscaleResult
}