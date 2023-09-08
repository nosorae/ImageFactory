package com.yessorae.imagefactory.ui.screen.main.tti

import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.SaveAlt
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.ui.components.item.IconWithText
import com.yessorae.imagefactory.ui.components.item.common.BaseDialog
import com.yessorae.imagefactory.ui.components.item.common.ImageLoadError
import com.yessorae.imagefactory.ui.components.layout.ImageLoadingLayout
import com.yessorae.imagefactory.ui.components.layout.LoadingLayout
import com.yessorae.imagefactory.ui.screen.main.tti.model.TxtToImgResultDialog
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.ui.theme.Gray400
import com.yessorae.imagefactory.util.compose.Margin
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ResultDialogScreen(
    dialog: TxtToImgResultDialog,
    loading: Boolean,
    onClickUpscale: (Bitmap?) -> Unit,
    onClickRetry: () -> Unit,
    onClickSave: () -> Unit,
    onClickCancel: () -> Unit
) {

    val context = LocalContext.current

    val imageUrl = dialog.result?.outputUrls?.firstOrNull()
    val painterState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .crossfade(true)
            .data(imageUrl)
            .build()
    )

    var bitmap: Bitmap? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = Unit) {
        launch {
            snapshotFlow {
                painterState.state
            }.collectLatest { state ->
                if (state is AsyncImagePainter.State.Success) {
                    bitmap = state.result.drawable.toBitmap()
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
                        Text(text = stringResource(id = R.string.common_state_generate_image))
                    } else {
                        when (painterState.state) {
                            is AsyncImagePainter.State.Loading -> {
                                Text(text = stringResource(id = R.string.common_state_load_image))
                            }

                            is AsyncImagePainter.State.Error -> {
                                Text(text = stringResource(id = R.string.common_state_load_image_failed))
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
                        onClick = onClickRetry,
                        modifier = Modifier.weight(1f)

                    )
                    IconWithText(
                        imageVector = Icons.Default.SaveAlt,
                        text = stringResource(id = R.string.result_dialog_option_save),
                        onClick = onClickSave,
                        modifier = Modifier.weight(1f)
                    )
                    IconWithText(
                        imageVector = Icons.Default.AutoAwesome,
                        text = stringResource(id = R.string.result_dialog_option_upscale),
                        onClick = {
                            onClickUpscale(bitmap)
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