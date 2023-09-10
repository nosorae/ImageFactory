package com.yessorae.data.remote.firebase

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.storage.StorageReference
import com.yessorae.data.remote.firebase.model.ImageUploadResponse
import kotlinx.coroutines.flow.Flow
import java.io.FileInputStream

interface FireStorageService {
    val storageRef: StorageReference

    fun uploadImage(
        bitmap: Bitmap,
        path: String,
        name: String
    ): Flow<ImageUploadResponse>

    fun uploadImage(
        stream: FileInputStream,
        path: String,
        name: String
    ): Flow<ImageUploadResponse>

    fun uploadImage(
        uri: Uri,
        path: String,
        name: String
    ): Flow<ImageUploadResponse>
}
