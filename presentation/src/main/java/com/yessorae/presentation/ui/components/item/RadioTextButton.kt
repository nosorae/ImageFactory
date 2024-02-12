package com.yessorae.presentation.ui.components.item

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.presentation.ui.screen.main.tti.model.TextOption
import com.yessorae.presentation.ui.theme.Dimen
import com.yessorae.presentation.util.compose.ColumnPreview
import com.yessorae.presentation.util.compose.Padding

@Composable
fun RadioTextButton(
    modifier: Modifier = Modifier,
    model: TextOption,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = model.selected, onClick = onClick)
        Padding(margin = Dimen.space_4)
        Text(text = model.displayName)
    }
}

@Preview
@Composable
fun RadioTextButtonPreview() {
    ColumnPreview {
//        RadioTextButton(
//            modifier = Modifier,
//            model = SizeOption.mock()[0]
//        )
//        RadioTextButton(
//            modifier = Modifier,
//            model = SizeOption.mock()[1]
//        )
//        RadioTextButton(
//            modifier = Modifier,
//            model = SizeOption.mock()[2]
//        )
    }
}
