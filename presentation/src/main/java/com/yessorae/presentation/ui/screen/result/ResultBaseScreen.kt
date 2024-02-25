package com.yessorae.presentation.ui.screen.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ResultBaseScreen(
    backgroundImageUrl: String?,
    imageContent: @Composable () -> Unit,
    optionContent: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ResultBackground(imageUrl = backgroundImageUrl)

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                imageContent()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.DarkGray
                            )
                        )
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                optionContent()
            }
        }
    }
}

@Composable
private fun ResultBackground(imageUrl: String?) {
    val context = LocalContext.current
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
}
