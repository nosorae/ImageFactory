package com.yessorae.domain.repository

import com.yessorae.domain.model.UpscaleResult

interface ImageEditingRepository {
    suspend fun upscaleImage(
        url: String,
        scale: Int = 4,
        faceEnhance: Boolean = true
    ): UpscaleResult
}