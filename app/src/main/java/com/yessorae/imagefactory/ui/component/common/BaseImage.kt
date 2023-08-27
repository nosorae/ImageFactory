package com.yessorae.imagefactory.ui.component.common

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import com.yessorae.common.Logger

@Composable
fun BaseImage(
    model: Any,
    modifier: Modifier
) {
    val context = LocalContext.current
    SubcomposeAsyncImage(
        model = model,
        contentDescription = null,
        imageLoader = ImageLoader.Builder(context)
            .crossfade(true)
            .build(),
        loading = {
            CircularProgressIndicator() // todo replace
        },
        error = {
            Image(imageVector = Icons.Outlined.Close, contentDescription = null) // todo replace
        },
        onError = { error ->
            Logger.recordException(error.result.throwable)
        },
        modifier = modifier.clip(shape = MaterialTheme.shapes.medium),
        contentScale = ContentScale.Crop
    )
}