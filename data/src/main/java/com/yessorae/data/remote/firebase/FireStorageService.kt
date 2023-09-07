package com.yessorae.data.remote.firebase

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.yessorae.common.Logger
import com.yessorae.data.remote.firebase.model.ImageUploadResponse
import com.yessorae.data.util.NetworkResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.ByteArrayOutputStream
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