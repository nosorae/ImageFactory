package com.yessorae.imagefactory.ui.screen.main.history

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TxtToImgHistoryScreen(
    viewModel: TxtToImgHistoryViewModel = hiltViewModel()
) {
    val screenState by viewModel.state.collectAsState()

    when (val state = screenState) {
        is TxtToImgHistoryScreenState.Loading -> {

        }

        is TxtToImgHistoryScreenState.View -> {
            Text(text = state.histories.toString(), modifier = Modifier.fillMaxWidth())
        }

        is TxtToImgHistoryScreenState.Edit -> {

        }
    }

}