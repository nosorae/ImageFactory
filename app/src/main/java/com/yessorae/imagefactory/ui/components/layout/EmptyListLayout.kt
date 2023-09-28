package com.yessorae.imagefactory.ui.components.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.yessorae.imagefactory.util.StringModel

/**
 * 리스트를 보여주는 화면에서 리스트가 비었을 때 보여준다.
 * 빈 경우를 통일시키려는 의도로 slot api를 사용하진 않음.
 */
@Composable
fun EmptyListLayout(
    modifier: Modifier = Modifier,
    text: StringModel
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = text.getValue(),
            textAlign = TextAlign.Center
        )
    }
}