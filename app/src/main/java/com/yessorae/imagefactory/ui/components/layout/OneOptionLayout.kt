package com.yessorae.imagefactory.ui.components.layout

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.imagefactory.model.SizeOption
import com.yessorae.imagefactory.model.mock
import com.yessorae.imagefactory.ui.components.item.RadioTextButton
import com.yessorae.imagefactory.ui.components.item.model.Option
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.ui.util.compose.ColumnPreview

@Composable
fun OneOptionLayout(
    state: ScrollState = rememberScrollState(),
    options: List<Option>,
    onClick: (Option) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .horizontalScroll(state = state),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimen.space_4)
    ) {
        options.forEach { option ->
            RadioTextButton(
                modifier = Modifier,
                model = option,
                onClick = { onClick(option) }
            )
        }
    }
}

@Preview
@Composable
fun OneOptionLayoutPreview() {
    ColumnPreview {
        OneOptionLayout(options = SizeOption.mock())
    }
}