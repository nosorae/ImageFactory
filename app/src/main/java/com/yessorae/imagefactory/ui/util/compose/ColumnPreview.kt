package com.yessorae.imagefactory.ui.util.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yessorae.imagefactory.ui.theme.ImageFactoryTheme

@Composable
fun ColumnPreview(modifier: Modifier = Modifier, spacedBy: Dp = 0.dp, content: @Composable ColumnScope.() -> Unit) {
    ImageFactoryTheme {
        Surface(modifier = modifier) {
            Column(
                verticalArrangement = Arrangement.spacedBy(spacedBy),
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                content()
            }
        }
    }
}

@Composable
fun ThemePreview(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    ImageFactoryTheme {
        Surface(modifier = modifier) {
                content()
        }
    }
}
