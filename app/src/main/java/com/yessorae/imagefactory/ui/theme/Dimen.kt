package com.yessorae.imagefactory.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp

object Dimen {
    val line = 1.dp
    val space_2 = 2.dp
    val space_4 = 4.dp
    val space_8 = 8.dp
    val space_12 = 12.dp
    val space_16 = 16.dp
    val space_24 = 24.dp
    val side_padding = 16.dp

    val small_icon_size = 16.dp
    val medium_icon_size = 24.dp

    val image_radius = 4.dp
    val button_height = 64.dp

    val cover_size = 88.dp

    val carousel_padding_values = PaddingValues(
        top = space_4,
        bottom = space_12,
        start = space_16,
        end = space_16
    )

    val grid_padding_values = PaddingValues(
        top = space_4,
        bottom = cover_size,
        start = space_16,
        end = space_16
    )
}
