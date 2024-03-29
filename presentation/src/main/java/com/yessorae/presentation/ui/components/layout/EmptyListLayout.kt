package com.yessorae.presentation.ui.components.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

/**
 * 리스트를 보여주는 화면에서 리스트가 비었을 때 보여준다.
 * 빈 경우를 통일시키려는 의도로 slot api를 사용하진 않음.
 */
@Composable
fun EmptyListLayout(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = text,
            textAlign = TextAlign.Center
        )
    }
}
