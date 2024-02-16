package com.yessorae.presentation.util.helper

import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.yessorae.common.Logger
import com.yessorae.presentation.R
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

// TODO:: SR-N Memory leak 예상됨. 해결 해보기
@Singleton
class ImageHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ImageHelper {
    override fun downloadImage(url: String) {
        val dateText = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "${context.getString(R.string.app_name)}_$dateText.png"

        val request = DownloadManager.Request(Uri.parse(url))

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setTitle(fileName)
            .setDescription(context.getString(R.string.common_state_download_image))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // 알림 설정
            .setMimeType("image/*")
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                fileName
            )

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }

    override fun downloadImage(bitmap: Bitmap): String {
        val directory = context.filesDir
        val dateText = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "${context.getString(R.string.app_name)}_$dateText.png"
        val file = File(directory, fileName)
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()
        return file.absolutePath
    }

    override fun uriToBitmap(uri: Uri): Bitmap {
        // convert Uri to bitmap image.
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(
                context.contentResolver, uri
            )
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(
                context.contentResolver, uri
            )
        }.copy(Bitmap.Config.ARGB_8888, true)

//        return try {
//            val parcelFileDescriptor =
//                context.contentResolver.openFileDescriptor(uri, "r")
//            val fileDescriptor = parcelFileDescriptor?.fileDescriptor
//            var image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
//
//            // 이미지의 회전 정보를 읽어옵니다.
//            val inputStream = context.contentResolver.openInputStream(uri)
//            val exif = ExifInterface(inputStream!!)
//            val orientation = exif.getAttributeInt(
//                ExifInterface.TAG_ORIENTATION,
//                ExifInterface.ORIENTATION_NORMAL
//            )
//
//            // 필요한 경우 이미지를 회전시킵니다.
//            val matrix = Matrix()
//            when (orientation) {
//                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
//                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
//                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
//            }
//            image = Bitmap.createBitmap(image, 0, 0, image.width, image.height, matrix, true)
//
//            parcelFileDescriptor?.close()
//            inputStream.close()
//            image
//        } catch (e: IOException) {
//            Logger.recordException(e)
//            e.printStackTrace()
//            null
//        }
    }

    override fun uriToBitmapWithSizeLimit512(uri: Uri): Bitmap {
        val maxWidth = 512f
        var bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
        // reduce the size of image if it larger than maxWidth
        if (bitmap.width > maxWidth) {
            val scaleFactor = maxWidth / bitmap.width
            bitmap = Bitmap.createScaledBitmap(
                bitmap,
                (bitmap.width * scaleFactor).toInt(),
                (bitmap.height * scaleFactor).toInt(),
                false
            )
        }
        return if (bitmap.config == Bitmap.Config.ARGB_8888) {
            bitmap
        } else {
            bitmap.copy(Bitmap.Config.ARGB_8888, false)
        }
    }

    override fun toMultiPartBody(bitmap: Bitmap, name: String): MultipartBody.Part {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val requestFile = byteArray.toRequestBody("image/png".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(name, "image.png", requestFile) // todo check name
    }
}