package com.yessorae.domain.repository

import com.yessorae.domain.model.ImageUploadResponse
import com.yessorae.domain.util.StableDiffusionConstants
import kotlinx.coroutines.flow.Flow
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface ImageUploadRepository {
    suspend fun uploadImage(
        bitmap: ByteArray,
        path: String = StableDiffusionConstants.DEFAULT_SAVED_IMAGE_NAME,
        name: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    ): String

    suspend fun uploadImage(
        stream: FileInputStream,
        path: String,
        name: String
    ): String
}
