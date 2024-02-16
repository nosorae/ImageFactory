package com.yessorae.presentation.util.helper

import android.graphics.Bitmap
import android.net.Uri
import okhttp3.MultipartBody

interface ImageHelper {
    fun downloadImage(url: String)

    fun downloadImage(bitmap: Bitmap): String

    fun uriToBitmap(uri: Uri): Bitmap

    fun uriToBitmapWithSizeLimit512(uri: Uri): Bitmap


    fun toMultiPartBody(bitmap: Bitmap, name: String): MultipartBody.Part
}


interface SegmentationHelper {

}
