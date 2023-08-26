package com.yessorae.imagefactory.ui.screen.tti

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.yessorae.common.Logger
import com.yessorae.common.replaceDomain

@Composable
fun TxtToImgScreen(
    viewModel: TxtToImgViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Button(onClick = { viewModel.generateImage() }) {
            Text(text = "Generate Image")
        }
        when (val state = uiState) {
            is TxtToImageUiState.Input -> {
                Text(text = "Input")
            }

            is TxtToImageUiState.Loading -> {
                Text(text = "Loading")
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is TxtToImageUiState.Success -> {
                AsyncImage(
                    model = state.response.output.getOrNull(0)?.replaceDomain(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Green)
                        .aspectRatio(1f)
                )

            }

            is TxtToImageUiState.Error -> {
                Text(text = "Error")
            }
        }
    }



}

fun String?.parseString(): String? {
    Logger.temp("url: $this")
    return this
}