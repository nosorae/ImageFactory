package com.yessorae.imagefactory.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object Dimen {
    val line = 1.dp
    val space_2 = 2.dp
    val space_4 = 4.dp
    val space_8 = 8.dp
    val space_12 = 12.dp
    val space_16 = 16.dp
    val space_18 = 18.dp
    val space_24 = 24.dp
    val side_padding = 16.dp

    val small_icon_size = 16.dp
    val medium_icon_size = 24.dp

    val image_radius = 4.dp
    val button_height = 64.dp

    val cover_size = 88.dp

    val dialog_width = 312.dp
    val dialog_max_height = 280.dp
    val dialog_top_padding = 24.dp
    val dialog_bottom_padding = 20.dp
    val dialog_horizontal_padding = 20.dp
    val dialog_content_padding = 8.dp
    val dialog_content_button_padding = 20.dp

    val bottom_sheet_top_padding = 56.dp

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

    val title_padding_modifier = Modifier
        .padding(horizontal = Dimen.space_16)
        .padding(top = Dimen.space_24, bottom = Dimen.space_4)
}
