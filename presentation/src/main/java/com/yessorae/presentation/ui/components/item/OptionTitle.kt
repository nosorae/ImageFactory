package com.yessorae.presentation.ui.components.item

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
import com.yessorae.presentation.ui.theme.Dimen
import com.yessorae.presentation.util.compose.ColumnPreview
import com.yessorae.presentation.util.compose.rememberDebouncedEvent

@Composable
fun OptionTitle(
    modifier: Modifier = Modifier,
    text: String
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
    text: String,
    trailingText: String,
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
    text: String,
    trailer: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
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
fun MoreButton(text: String, onClick: () -> Unit) {
    val singleEvent = rememberDebouncedEvent()
    TextButton(onClick = {
        singleEvent.processEvent(onClick)
    }) {
        Text(text = text, style = MaterialTheme.typography.bodySmall)
    }
}

@Preview
@Composable
fun OptionTitlePreview() {
    ColumnPreview {
        OptionTitle(
            modifier = Modifier,
            text = "이런 느낌이었으면 좋겠어요."
        )

        OptionTitleWithMore(
            modifier = Modifier,
            text = "이런 느낌이었으면 좋겠어요.",
            trailingText = "더보기"
        )
    }
}
