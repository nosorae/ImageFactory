package com.yessorae.imagefactory.ui.screen.tti

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.ui.components.item.IconWithText
import com.yessorae.imagefactory.ui.screen.tti.model.TxtToImgResultDialog
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.ui.util.compose.Margin

@Composable
fun ResultDialogScreen(
    dialog: TxtToImgResultDialog,
    onClickUpscale: () -> Unit,
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

    BackHandler {
        onClickCancel()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                // do nothing
            }
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
                Image(
                    painter = painterState,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimen.space_16)
                        .aspectRatio(dialog.ratio)
                        .clip(MaterialTheme.shapes.medium)
                )
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
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                IconWithText(
                    imageVector = Icons.Default.Replay,
                    text = stringResource(id = R.string.result_dialog_option_retry),
                    onClick = onClickRetry
                )
                IconWithText(
                    imageVector = Icons.Default.SaveAlt,
                    text = stringResource(id = R.string.result_dialog_option_save),
                    onClick = onClickSave
                )
                IconWithText(
                    imageVector = Icons.Default.AutoAwesome,
                    text = stringResource(id = R.string.result_dialog_option_upscale),
                    onClick = onClickUpscale
                )
            }

            Margin(margin = Dimen.dialog_bottom_padding)
        }

        IconButton(
            onClick = onClickCancel,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = Dimen.space_16)
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
    }
}