package com.yessorae.data.remote.firebase

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yessorae.common.Logger
import com.yessorae.data.remote.firebase.model.ImageUploadResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import javax.inject.Inject

class FireStorageServiceImpl @Inject constructor() : FireStorageService {
    override val storageRef = Firebase.storage.reference

    override fun uploadImage(
        bitmap: Bitmap,
        path: String,
        name: String
    ): Flow<ImageUploadResponse> = callbackFlow {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()

        val ref = storageRef.child("${path}/$name")

        ref.putBytes(data)
            .addOnProgressListener { progressInfo ->
                Logger.data("uploadImage bitmap - pro.bytesTransferred / pro.totalByteCount ${progressInfo.bytesTransferred / progressInfo.totalByteCount.toFloat()}")
            }
            .continueWithTask { task ->
                if (task.isSuccessful.not()) {
                    task.exception?.let {
                        close(cause = it)
                    }
                }
                ref.downloadUrl
            }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySendBlocking(ImageUploadResponse(uri = task.result))
                    close()
                } else {
                    task.exception?.let {
                        close(cause = it)
                    }

                }
            }

        awaitClose {
            Logger.data(message = "uploadImage bitmap - awaitClose")
            baos.close()
        }
    }

    override fun uploadImage(
        stream: FileInputStream,
        path: String,
        name: String
    ): Flow<ImageUploadResponse> = callbackFlow {
        storageRef
            .child("${path}/$name")
            .putStream(stream).addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // ...
            }
    }

    override fun uploadImage(
        uri: Uri,
        path: String,
        name: String
    ): Flow<ImageUploadResponse> = callbackFlow {
        storageRef
            .child("${path}/$name")
            .putFile(uri).addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // ...
            }
    }
}

