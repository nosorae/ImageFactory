package com.yessorae.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

fun Bitmap.bitmapToByteArray(): ByteArray? {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}

fun ByteArray.byteArrayToBitmap(): Bitmap? {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}
