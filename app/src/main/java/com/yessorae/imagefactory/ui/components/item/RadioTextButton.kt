package com.yessorae.imagefactory.ui.components.item

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.imagefactory.model.SizeOption
import com.yessorae.imagefactory.model.mock
import com.yessorae.imagefactory.ui.components.item.model.Option
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.util.compose.ColumnPreview
import com.yessorae.imagefactory.util.compose.Margin

@Composable
fun RadioTextButton(
    modifier: Modifier = Modifier,
    model: Option,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = model.selected, onClick = onClick)
        Margin(margin = Dimen.space_4)
        Text(text = model.title.getValue())
    }
}

@Preview
@Composable
fun RadioTextButtonPreview() {
    ColumnPreview {
        RadioTextButton(
            modifier = Modifier,
            model = SizeOption.mock()[0]
        )
        RadioTextButton(
            modifier = Modifier,
            model = SizeOption.mock()[1]
        )
        RadioTextButton(
            modifier = Modifier,
            model = SizeOption.mock()[2]
        )
    }
}
