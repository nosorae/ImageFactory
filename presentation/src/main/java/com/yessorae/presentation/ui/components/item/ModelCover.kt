package com.yessorae.presentation.ui.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.domain.model.parameter.SDModel
import com.yessorae.presentation.ui.components.item.common.SelectableImage
import com.yessorae.presentation.ui.screen.main.tti.model.ModelOption
import com.yessorae.presentation.ui.screen.main.tti.model.asOption
import com.yessorae.presentation.ui.theme.Dimen
import com.yessorae.presentation.ui.theme.Gray200
import com.yessorae.presentation.util.compose.ColumnPreview
import com.yessorae.presentation.util.compose.Margin

@Composable
fun ModelCover(
    modifier: Modifier = Modifier,
    model: ModelOption,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .width(Dimen.cover_size)
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                onClick()
            }
    ) {
        SelectableImage(
            model = model.model.imgUrl ?: "",
            modifier = Modifier
                .size(Dimen.cover_size),
            selected = model.selected
        )

        Margin(margin = Dimen.space_4)

        Text(
            text = model.model.displayName,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        Margin(margin = Dimen.space_2)
    }
}

@Composable
fun ModelCoverPlaceholder(
    modifier: Modifier = Modifier
) {
    val color = Gray200

    Column(
        modifier = modifier
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .background(
                    shape = MaterialTheme.shapes.medium,
                    color = color
                )
                .size(Dimen.cover_size)
        )
        Margin(margin = Dimen.space_4)

        Text(
            text = "",
            modifier = Modifier
                .width(Dimen.cover_size)
                .background(
                    shape = MaterialTheme.shapes.medium,
                    color = color
                ),
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        Margin(margin = Dimen.space_2)
    }
}

@Composable
fun GridModelCover(
    modifier: Modifier = Modifier,
    model: ModelOption,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onClick()
            }
    ) {
        SelectableImage(
            model = model.model.imgUrl ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            selected = model.selected
        )

        Margin(margin = Dimen.space_4)

        Text(
            text = model.model.displayName,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Margin(margin = Dimen.space_2)
    }
}

@Preview
@Composable
fun ModelCoverPreview() {
    ColumnPreview {
        ModelCover(
            model = SDModel.mock()[0].asOption()
        )
    }
}
