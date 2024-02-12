package com.yessorae.presentation.ui.components.item.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.yessorae.presentation.ui.theme.Dimen
import com.yessorae.presentation.ui.theme.PrimaryBrush

@Composable
fun ImageListItem(
    model: Any?,
    modifier: Modifier
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(model)
            .size(256, 256) // Set the target size to load the image at.
            .crossfade(true)
            .build()
    )
    Image(
        painter = painter,
        modifier = modifier
            .clip(shape = MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.outline),
        contentDescription = null,
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
