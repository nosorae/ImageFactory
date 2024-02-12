package com.yessorae.presentation.ui.components.item

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.domain.model.parameter.Prompt
import com.yessorae.presentation.ui.screen.main.tti.model.TextOption
import com.yessorae.presentation.ui.screen.main.tti.model.asOption
import com.yessorae.presentation.util.compose.ColumnPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptChip(
    modifier: Modifier = Modifier,
    model: TextOption,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    InputChip(
        modifier = modifier.pointerInput(Unit){
            detectTapGestures(
                onLongPress = {
                    onLongClick()
                }
            )
        },
        selected = model.selected,
        onClick = onClick,
        label = {
            Text(text = model.displayName)
        },
        shape = CircleShape
    )
}

@Preview
@Composable
fun PromptChipPreview() {
    val list = remember {
        Prompt.mock().map(Prompt::asOption)
    }
    ColumnPreview {
        PromptChip(
            model = list.first()
        )
        PromptChip(
            model = list.last()
        )
    }
}
