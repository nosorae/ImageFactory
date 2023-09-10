package com.yessorae.imagefactory.ui.components.layout

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowLeft
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Precision
import com.yessorae.common.Logger
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.ui.theme.Dimen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ImageCompareLayout(
    modifier: Modifier = Modifier,
    before: String?,
    after: String,
    onAfterLoadingComplete: (Bitmap) -> Unit
) {
    val density = LocalDensity.current

    val screenWidth = with(density) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }

    var offset by remember { mutableStateOf(0f) }

    var indicatorSize by remember {
        mutableStateOf(0f)
    }

    val context = LocalContext.current

    val afterImageRequest = ImageRequest.Builder(context = context)
        .size(coil.size.Size.ORIGINAL)
        .precision(Precision.EXACT)
        .allowHardware(true)
        .crossfade(true)
        .data(after)
        .build()

    val beforeImageRequest = ImageRequest.Builder(context = context)
        .allowHardware(true)
        .crossfade(true)
        .data(before)
        .build()

    val painterState = rememberAsyncImagePainter(
        model = afterImageRequest
    )

    LaunchedEffect(key1 = Unit) {
        launch {
            snapshotFlow {
                painterState.state
            }.collectLatest { state ->
                if (state is AsyncImagePainter.State.Success) {
                    onAfterLoadingComplete(state.result.drawable.toBitmap())
                }
            }
        }
    }

    Box(
        modifier = modifier
    ) {
        SubcomposeAsyncImage(
            model = beforeImageRequest,
            contentDescription = stringResource(id = R.string.result_after),
            modifier = Modifier
                .background(color = Color.Transparent)
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            onError = {
                Logger.presentation("after : ${it.result.throwable}", error = true)
            }
        )

        SubcomposeAsyncImage(
            model = afterImageRequest,
            contentDescription = stringResource(id = R.string.result_before),
            modifier = Modifier
                .background(color = Color.Transparent)
                .fillMaxSize()
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithCache {
                    val path = Path()
                    path.addRect(
                        Rect(
                            topLeft = Offset(0f, 0f),
                            bottomRight = Offset(size.width, size.height)
                        )
                    )
                    onDrawWithContent {
                        clipPath(path) {
                            this@onDrawWithContent.drawContent()
                        }
                        drawRect(
                            topLeft = Offset(offset + (indicatorSize / 2), 0f),
                            size = Size(screenWidth, size.height),
                            color = Color.Transparent,
                            blendMode = BlendMode.Clear
                        )
                    }
                }
                .clip(RoundedCornerShape(Dimen.image_radius)),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            onError = {
                Logger.presentation("before : ${it.result.throwable}", error = true)
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
                .offset(x = with(density) { offset.toDp() }, y = 0.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        offset += dragAmount
                        change.consume()
                    }
                }
                .graphicsLayer {
                    indicatorSize = size.width
                },
            contentAlignment = Alignment.Center
        ) {
            val lineColor = Color.White.copy(alpha = 0.8f)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(color = lineColor)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.result_before),
                    style = MaterialTheme.typography.labelSmall,
                    color = lineColor
                )
                Icon(
                    imageVector = Icons.Outlined.ArrowLeft,
                    contentDescription = null,
                    modifier = Modifier.size(Dimen.small_icon_size),
                    tint = lineColor
                )
                Spacer(modifier = Modifier.width(Dimen.space_4))
                Icon(
                    imageVector = Icons.Outlined.ArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(Dimen.small_icon_size),
                    tint = lineColor
                )
                Text(
                    text = stringResource(id = R.string.result_after),
                    style = MaterialTheme.typography.labelSmall,
                    color = lineColor
                )
            }
        }
    }
}
