package com.yessorae.imagefactory.ui.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.imagefactory.ui.component.model.Chip
import com.yessorae.imagefactory.ui.util.compose.BasePreview
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptChip(
    modifier: Modifier = Modifier,
    model: Chip,
    onClick: () -> Unit = {}
) {
    InputChip(
        modifier = modifier,
        selected = model.selected,
        onClick = onClick,
        label = {
            Text(text = model.text.getValue())
        },
        shape = CircleShape
    )
}

@Preview
@Composable
fun PromptChipPreview() {
    BasePreview {
        PromptChip(
            model = object : Chip {
                override val text: StringModel
                    get() = TextString("PromptChipPreview")
                override val selected: Boolean
                    get() = false
            }
        )
        PromptChip(
            model = object : Chip {
                override val text: StringModel
                    get() = TextString("PromptChipPreview")
                override val selected: Boolean
                    get() = true
            }
        )
    }
}