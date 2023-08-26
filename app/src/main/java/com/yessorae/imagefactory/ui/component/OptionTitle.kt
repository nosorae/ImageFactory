package com.yessorae.imagefactory.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.ui.util.ResString
import com.yessorae.imagefactory.ui.util.StringModel

@Composable
fun OptionTitle(
    modifier: Modifier,
    text: StringModel
) {
    Text(
        text = text.getValue(),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimen.space_16)
            .padding(top = Dimen.space_24, bottom = Dimen.space_8),
        style = MaterialTheme.typography.headlineSmall,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
fun OptionTitleWithMore(
    modifier: Modifier,
    text: StringModel,
    onClickMore: () -> Unit
) {
    OptionTitle(
        modifier = modifier,
        text = text,
        trailer = {
            MoreButton(text = ResString(R.string.common_see_more), onClick = onClickMore)
        }
    )
}

@Composable
fun OptionTitle(
    modifier: Modifier,
    text: StringModel,
    trailer: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = Dimen.space_16)
            .padding(top = Dimen.space_24, bottom = Dimen.space_8)
    ) {
        Text(
            text = text.getValue(),
            modifier = Modifier
                .weight(1f),
            style = MaterialTheme.typography.headlineSmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        trailer()
    }
}

@Composable
fun MoreButton(text: StringModel, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(text = text.getValue(), style = MaterialTheme.typography.titleSmall)
    }
}