package com.yessorae.domain.repository

import com.yessorae.domain.model.ImageUploadResponse
import kotlinx.coroutines.flow.Flow
import java.io.FileInputStream

interface ImageUploadRepository {
    suspend fun uploadImage(
        bitmap: ByteArray,
        path: String,
        name: String
    ): String

    suspend fun uploadImage(
        stream: FileInputStream,
        path: String,
        name: String
    ): String
}
