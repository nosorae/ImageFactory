package com.yessorae.presentation.ui.components.layout.option

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.presentation.ui.components.item.RadioTextButton
import com.yessorae.presentation.ui.screen.main.tti.model.Option
import com.yessorae.presentation.ui.screen.main.tti.model.TextOption
import com.yessorae.presentation.ui.theme.Dimen
import com.yessorae.presentation.util.compose.ColumnPreview

@Composable
fun RadioOptionLayout(
    state: ScrollState = rememberScrollState(),
    options: () -> List<TextOption>,
    onClick: (TextOption) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .horizontalScroll(state = state),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimen.space_4)
    ) {
        options().forEach { option ->
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
//        RadioOptionLayout(options = SizeOption.mock())
    }
}
