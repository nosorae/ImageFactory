package com.yessorae.imagefactory.ui.components.item

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString
import com.yessorae.imagefactory.ui.util.compose.ColumnPreview

@Composable
fun OptionTitle(
    modifier: Modifier = Modifier,
    text: StringModel
) {
    OptionTitle(
        modifier = modifier
            .padding(horizontal = Dimen.space_16)
            .padding(top = Dimen.space_24, bottom = Dimen.space_4),
        text = text,
        trailer = {}
    )
}

@Composable
fun OptionTitleWithMore(
    modifier: Modifier = Modifier,
    text: StringModel,
    trailingText: StringModel,
    onClickMore: () -> Unit = {}
) {
    OptionTitle(
        modifier = modifier
            .padding(start = Dimen.space_16)
            .padding(top = Dimen.space_24, bottom = Dimen.space_4),
        text = text,
        trailer = {
            MoreButton(text = trailingText, onClick = onClickMore)
        }
    )
}

@Composable
fun OptionTitle(
    modifier: Modifier,
    text: StringModel,
    trailer: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text.getValue(),
            modifier = Modifier
                .weight(1f),
            style = MaterialTheme.typography.titleSmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        trailer()
    }
}

@Composable
fun MoreButton(text: StringModel, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(text = text.getValue(), style = MaterialTheme.typography.bodySmall)
    }
}

@Preview
@Composable
fun OptionTitlePreview() {
    ColumnPreview {
        OptionTitle(
            modifier = Modifier,
            text = TextString("이런 느낌이었으면 좋겠어요.")
        )

        OptionTitleWithMore(
            modifier = Modifier,
            text = TextString("이런 느낌이었으면 좋겠어요."),
            trailingText = TextString("더보기")
        )
    }
}
