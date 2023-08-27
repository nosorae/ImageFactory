package com.yessorae.imagefactory.ui.component.model

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.DrawableRes
import com.yessorae.imagefactory.ui.util.StringModel
import okhttp3.HttpUrl
import java.io.File
import java.nio.ByteBuffer

interface Cover {
    /**
     * from [coil.compose.requestOf] and [coil.request.ImageRequest.Builder.data]
     * Set the data to load.
     *
     * The default supported data types are:
     * - [String] (mapped to a [Uri])
     * - [Uri] ("android.resource", "content", "file", "http", and "https" schemes only)
     * - [HttpUrl]
     * - [File]
     * - [DrawableRes]
     * - [Drawable]
     * - [Bitmap]
     * - [ByteArray]
     * - [ByteBuffer]
     */
    val model: Any
    val title: StringModel
    val selected: Boolean
}
