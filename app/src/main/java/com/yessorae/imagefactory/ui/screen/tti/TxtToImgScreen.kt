package com.yessorae.imagefactory.ui.screen.tti

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TxtToImgScreen(
    viewModel: TxtToImgViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Button(onClick = { viewModel.generateImage() }) {
            Text(text = "Generate Image")
        }
    }
}

@Composable
fun SelectionScreen() {
}
