package com.yessorae.imagefactory.ui.components.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.imagefactory.ui.theme.AlertRed
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.util.StringModel
import com.yessorae.imagefactory.util.TextString
import com.yessorae.imagefactory.util.compose.Margin

@Composable
fun WarningLayout(
    text: StringModel
) {
    Row(
        modifier = Modifier.padding(horizontal = Dimen.space_16, vertical = Dimen.space_8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = Color.Yellow)
        Margin(margin = Dimen.space_8)
        Text(text = text.getValue(), color = AlertRed)
    }
}

@Preview
@Composable
fun WarningLayoutPreview() {
    WarningLayout(text = TextString("19금 이미지가 나올 수도 있어요"))
}
