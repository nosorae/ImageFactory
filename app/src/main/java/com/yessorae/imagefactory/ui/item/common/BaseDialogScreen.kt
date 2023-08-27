package com.yessorae.imagefactory.ui.item.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yessorae.imagefactory.ui.theme.Dimen

@Composable
fun BaseDialogScreen(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .width(Dimen.dialog_width)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = Dimen.dialog_horizontal_padding)
            .padding(top = Dimen.dialog_top_padding, bottom = Dimen.dialog_bottom_padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}