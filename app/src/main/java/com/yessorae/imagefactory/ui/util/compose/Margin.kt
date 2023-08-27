package com.yessorae.imagefactory.ui.util.compose

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun ColumnScope.Margin(margin: Dp) {
    Spacer(modifier = Modifier.height(margin))
}

@Composable
fun RowScope.Margin(margin: Dp) {
    Spacer(modifier = Modifier.width(margin))
}