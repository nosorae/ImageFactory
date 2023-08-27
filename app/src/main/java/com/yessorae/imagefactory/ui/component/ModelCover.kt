package com.yessorae.imagefactory.ui.component

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import com.yessorae.common.Logger
import com.yessorae.imagefactory.ui.component.model.Cover
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.ui.util.MockData
import com.yessorae.imagefactory.ui.util.compose.BasePreview
import com.yessorae.imagefactory.ui.util.compose.Margin
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString

@Composable
fun ModelCover(
    modifier: Modifier = Modifier,
    model: Cover,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .width(Dimen.cover_size)
            .clickable {
                onClick()
            }
    ) {
        SubcomposeAsyncImage(
            model = model.model,
            contentDescription = null,
            imageLoader = ImageLoader.Builder(context)
                .crossfade(true)
                .build(),
            loading = {
                CircularProgressIndicator()
            },
            error = {
                Image(imageVector = Icons.Outlined.Close, contentDescription = null)
            },
            onError = { error ->
                Logger.recordException(error.result.throwable)
            },
            modifier = Modifier.size(Dimen.cover_size),
            contentScale = ContentScale.Crop
        )

        Margin(margin = Dimen.space_4)

        Text(
            text = model.title.getValue(),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun GridModelCover(
    modifier: Modifier = Modifier,
    model: Cover,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        SubcomposeAsyncImage(
            model = model.model,
            contentDescription = null,
            imageLoader = ImageLoader.Builder(context)
                .crossfade(true)
                .build(),
            loading = {
                CircularProgressIndicator()
            },
            error = {
                Image(imageVector = Icons.Outlined.Close, contentDescription = null)
            },
            onError = { error ->
                Logger.recordException(error.result.throwable)
            },
            modifier = Modifier.fillMaxWidth().aspectRatio(1f),
            contentScale = ContentScale.Crop
        )

        Margin(margin = Dimen.space_4)

        Text(
            text = model.title.getValue(),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun ModelCoverPreview() {
    BasePreview {
        ModelCover(
            model = object : Cover {
                override val model: Any = MockData.MOCK_IMAGE_URL
                override val title: StringModel = TextString("MidJourney V4")
            }
        )
    }
}
