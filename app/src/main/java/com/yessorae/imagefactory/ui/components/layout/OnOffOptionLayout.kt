package com.yessorae.imagefactory.ui.components.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString

@Composable
fun OnOffOptionLayout(
    modifier: Modifier,
    text: StringModel,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Row(
        modifier = modifier.padding(horizontal = Dimen.space_16, vertical = Dimen.space_8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text.getValue(), modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Preview
@Composable
fun OnOffOptionPreview() {
    var checked by remember {
        mutableStateOf(true)
    }
    OnOffOptionLayout(
        modifier = Modifier,
        text = TextString("프롬프트 자동 개선 활성화"),
        checked = checked,
        onCheckedChange = {
            checked = it
        }
    )
}
