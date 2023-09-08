package com.yessorae.imagefactory.ui.screen.main.tti

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.outlined.ArrowLeft
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Precision
import com.yessorae.common.Logger
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.ui.components.item.IconWithText
import com.yessorae.imagefactory.ui.components.item.common.BaseDialog
import com.yessorae.imagefactory.ui.components.item.common.ImageLoadError
import com.yessorae.imagefactory.ui.components.layout.ImageLoadingLayout
import com.yessorae.imagefactory.ui.screen.main.tti.model.TxtToImgOptionState
import com.yessorae.imagefactory.ui.screen.main.tti.model.TxtToImgResultDialog
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.util.compose.Margin
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ResultDialogScreen(
    dialog: TxtToImgResultDialog,
    loading: Boolean,
    onClickUpscale: (Bitmap?) -> Unit,
    onClickRetry: (TxtToImgOptionState) -> Unit,
    onClickSave: (String?) -> Unit,
    onClickCancel: () -> Unit
) {

    val context = LocalContext.current

    val imageUrl = dialog.result?.outputUrls?.firstOrNull()
    val upscaleImageUrl = dialog.upscaleResult?.outputUrl
    val painterState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .crossfade(true)
            .data(imageUrl)
            .build()
    )

    var bitmapForUpscale: Bitmap? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = Unit) {
        launch {
            snapshotFlow {
                painterState.state
            }.collectLatest { state ->
                if (state is AsyncImagePainter.State.Success) {
                    bitmapForUpscale = state.result.drawable.toBitmap()
                }
            }
        }
    }

    BaseDialog(
        onDismissRequest = onClickCancel,
        dismissOnClickOutside = false,
        fullScreen = true
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {

            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(
                        radiusX = 10.dp,
                        radiusY = 10.dp,
                        edgeTreatment = BlurredEdgeTreatment.Unbounded
                    ),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimen.space_16)
                            .aspectRatio(dialog.ratio)
                            .clip(MaterialTheme.shapes.medium),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            loading -> {
                                ImageLoadingLayout(modifier = Modifier.fillMaxSize())
                            }

                            painterState.state is AsyncImagePainter.State.Error -> {
                                ImageLoadError(
                                    modifier = Modifier
                                        .fillMaxSize()
                                )
                            }

                            imageUrl != null && dialog.upscaleResult != null -> {
                                ImageComparer(
                                    modifier = Modifier.fillMaxSize(),
                                    before = imageUrl,
                                    after = dialog.upscaleResult.outputUrl,
                                    onAfterLoadingComplete = { afterBitmap ->
                                        bitmapForUpscale = afterBitmap
                                    }
                                )
                            }

                            else -> {
                                Image(
                                    painter = painterState,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }

                    Margin(margin = Dimen.space_8)
                    if (dialog.result == null) {
                        Text(
                            text = stringResource(id = R.string.common_state_generate_image),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        when (painterState.state) {
                            is AsyncImagePainter.State.Loading -> {
                                Text(
                                    text = stringResource(id = R.string.common_state_load_image),
                                    textAlign = TextAlign.Center
                                )
                            }

                            is AsyncImagePainter.State.Error -> {
                                Text(
                                    text = stringResource(id = R.string.common_state_load_image_failed),
                                    textAlign = TextAlign.Center
                                )
                            }

                            else -> {
                                // do nothing
                            }
                        }
                    }
                }

                ResultOptionRow {
                    IconWithText(
                        imageVector = Icons.Default.Replay,
                        text = stringResource(id = R.string.result_dialog_option_retry),
                        onClick = {
                            onClickRetry(dialog.requestOption)
                        },
                        modifier = Modifier.weight(1f)

                    )
                    IconWithText(
                        imageVector = Icons.Default.SaveAlt,
                        text = stringResource(id = R.string.result_dialog_option_save),
                        onClick = {
                            onClickSave(upscaleImageUrl ?: imageUrl)
                        },
                        modifier = Modifier.weight(1f)
                    )
                    IconWithText(
                        imageVector = Icons.Default.AutoAwesome,
                        text = stringResource(id = R.string.result_dialog_option_upscale),
                        onClick = {
                            onClickUpscale(bitmapForUpscale)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .fillMaxWidth()
                    .height(Dimen.top_app_bar_height)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.DarkGray,
                                Color.Transparent,
                            )
                        )
                    ),
            ) {
                IconButton(
                    onClick = onClickCancel,
                    modifier = Modifier
                        .padding(top = Dimen.space_16)
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Composable
private fun ResultOptionRow(
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.DarkGray,
                    )
                )
            )
            .padding(bottom = Dimen.space_24),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        content()
    }
}

@Composable
fun ImageComparer(
    modifier: Modifier = Modifier,
    before: String,
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