package com.yessorae.imagefactory.ui.components.item.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.yessorae.common.Logger
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.ui.theme.PrimaryBrush

@Composable
fun ImageListItem(
    model: Any?,
    modifier: Modifier
) {
    val context = LocalContext.current
    // AsyncImage로 전환
    // Subcomposition is less performant than regular composition
    // so this composable may not be suitable for parts of your UI where high performance is critical (e.g. lists).
    SubcomposeAsyncImage(
        model = model,
        contentDescription = null,
        imageLoader = ImageLoader.Builder(context)
            .crossfade(true)
            .build(),
        loading = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(size = 24.dp)) // todo replace
            }
        },
        error = {
            ImageLoadError(
                modifier = Modifier
                    .size(Dimen.small_icon_size)
            )
        },
        onError = { error ->
            Logger.recordException(error.result.throwable)
        },
        modifier = modifier.clip(shape = MaterialTheme.shapes.medium),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun SelectableImage(
    modifier: Modifier = Modifier,
    model: Any,
    selected: Boolean
) {
    val shapeModifier = if (selected) {
        Modifier
            .border(
                width = Dimen.line,
                brush = PrimaryBrush,
                shape = MaterialTheme.shapes.medium
            )
    } else {
        Modifier
    }

    Box(modifier = modifier.then(shapeModifier)) {
        ImageListItem(
            model = model,
            modifier = Modifier.fillMaxSize()
        )

        if (selected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = PrimaryBrush,
                        shape = MaterialTheme.shapes.medium,
                        alpha = 0.5f
                    )
            )
        }
    }
}
