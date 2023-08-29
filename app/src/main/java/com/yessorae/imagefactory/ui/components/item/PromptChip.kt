package com.yessorae.imagefactory.ui.components.item

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.imagefactory.ui.components.item.model.Option
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString
import com.yessorae.imagefactory.ui.util.compose.ColumnPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptChip(
    modifier: Modifier = Modifier,
    model: Option,
    onClick: () -> Unit = {}
) {
    InputChip(
        modifier = modifier,
        selected = model.selected,
        onClick = onClick,
        label = {
            Text(text = model.title.getValue())
        },
        shape = CircleShape
    )
}

@Preview
@Composable
fun PromptChipPreview() {
    ColumnPreview {
        PromptChip(
            model = object : Option {
                override val id: String
                    get() = "0"
                override val title: StringModel
                    get() = TextString("PromptChipPreview")
                override val selected: Boolean
                    get() = false
            }
        )
        PromptChip(
            model = object : Option {
                override val id: String
                    get() = "0"
                override val title: StringModel
                    get() = TextString("PromptChipPreview")
                override val selected: Boolean
                    get() = true
            }
        )
    }
}
