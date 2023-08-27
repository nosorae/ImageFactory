package com.yessorae.imagefactory.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yessorae.imagefactory.ui.util.BasePreview
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptChip(
    modifier: Modifier = Modifier,
    text: StringModel,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    InputChip(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        label = {
            Text(text = text.getValue())
        },
        shape = CircleShape
    )
}

@Preview
@Composable
fun PromptChipPreview() {
    BasePreview {
        PromptChip(text = TextString("PromptChipPreview"), selected = true)
        PromptChip(text = TextString("PromptChipPreview"), selected = false)
    }
}