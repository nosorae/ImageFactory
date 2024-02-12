package com.yessorae.data.repository

import com.google.firebase.storage.StorageReference
import com.yessorae.common.Logger
import com.yessorae.domain.repository.ImageUploadRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.FileInputStream
import javax.inject.Inject
import kotlin.coroutines.resume

class ImageUploadRepositoryImpl @Inject constructor(
    private val storageReference: StorageReference
) : ImageUploadRepository {

    override suspend fun uploadImage(
        stream: FileInputStream,
        path: String,
        name: String
    ): String = suspendCancellableCoroutine { continuation ->
        val ref = storageReference.child("$path/$name")
        ref.putStream(stream)
            .addOnProgressListener { progressInfo ->
                Logger.data("uploadImage bitmap - pro.bytesTransferred / pro.totalByteCount ${progressInfo.bytesTransferred / progressInfo.totalByteCount.toFloat()}")
            }
            .continueWithTask { task ->
                if (task.isSuccessful.not()) {
                    task.exception?.let {
                        continuation.cancel(cause = it)
                    }
                }
                ref.downloadUrl
            }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(task.result.toString())
                } else {
                    task.exception?.let {
                        continuation.cancel(cause = it)
                    }
                }
            }
    }

    override suspend fun uploadImage(
        bitmap: ByteArray,
        path: String,
        name: String
    ): String = suspendCancellableCoroutine { continuation ->
        val ref = storageReference.child("$path/$name")

        ref.putBytes(bitmap)
            .addOnProgressListener { progressInfo ->
                Logger.data("uploadImage bitmap - pro.bytesTransferred / pro.totalByteCount ${progressInfo.bytesTransferred / progressInfo.totalByteCount.toFloat()}")
            }
            .continueWithTask { task ->
                if (task.isSuccessful.not()) {
                    task.exception?.let {
                        continuation.cancel(cause = it)
                    }
                }
                ref.downloadUrl
            }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(task.result.toString())
                } else {
                    task.exception?.let {
                        continuation.cancel(cause = it)
                    }
                }
            }
    }
}
