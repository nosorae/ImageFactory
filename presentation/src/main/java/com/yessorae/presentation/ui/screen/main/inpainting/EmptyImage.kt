package com.yessorae.presentation.ui.screen.main.inpainting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yessorae.presentation.R

val imageRadius = 48.dp
@Composable
fun EmptyImage(modifier: Modifier = Modifier) {
    val outlineColor = MaterialTheme.colorScheme.outline
    val displayWidth = LocalConfiguration.current.screenWidthDp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(imageRadius))
            .drawBehind {
                drawRoundRect(
                    color = outlineColor,
                    style = Stroke(
                        width = 24f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(64f, 48f), 0f)
                    ),
                    cornerRadius = CornerRadius(imageRadius.toPx())
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.ImageSearch,
                contentDescription = stringResource(id = R.string.common_add_image),
                tint = outlineColor,
                modifier = Modifier
                    .width(displayWidth.dp / 3)
                    .aspectRatio(1f)
            )
        }
    }
}

@Preview(widthDp = 320)
@Composable
fun EmptyImagePreview() {
    EmptyImage()
}