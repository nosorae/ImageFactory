package com.yessorae.domain.model.option

import java.io.File
import java.nio.ByteBuffer

interface CoverOption : Option {
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
    val image: Any
    override val id: String
    override val title: String
    override val selected: Boolean
}
