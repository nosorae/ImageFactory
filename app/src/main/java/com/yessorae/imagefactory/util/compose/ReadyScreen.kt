package com.yessorae.imagefactory.util.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ReadyScreen(
    screenName: String
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = screenName, style = MaterialTheme.typography.titleLarge, color = Color.Black)
    }
}
