package com.yessorae.imagefactory.ui.components.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingLayout(
    modifier: Modifier = Modifier

) {
    // 이미지 여러장 0.2초정도마다 교체하는 로딩화면 만들 예정
    CircularProgressIndicator(modifier = modifier)
}

@Composable
fun StableDiffusionLoadingLayout(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        // 이미지 여러장 0.2초정도마다 교체하는 로딩화면 만들 예정
        CircularProgressIndicator()
    }
}

@Composable
fun UpscaleLoadingLayout(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        // 이미지 여러장 0.2초정도마다 교체하는 로딩화면 만들 예정
        CircularProgressIndicator()
    }
}
