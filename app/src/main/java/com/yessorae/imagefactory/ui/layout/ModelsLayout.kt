package com.yessorae.imagefactory.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.imagefactory.model.SDModel
import com.yessorae.imagefactory.model.mock
import com.yessorae.imagefactory.ui.component.ModelCover
import com.yessorae.imagefactory.ui.component.model.Cover
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.ui.util.compose.ColumnPreview


@Composable
fun ModelsLayout(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    models: List<Cover>,
    onClick: (Cover) -> Unit = {}
) {
    LazyRow(
        modifier = modifier,
        state = state,
        contentPadding = Dimen.carousel_padding_values,
        horizontalArrangement = Arrangement.spacedBy(Dimen.space_4),
    ) {
        itemsIndexed(items = models) { _, item ->
            ModelCover(
                model = item,
                onClick = { onClick(item) }
            )
        }
    }
}

@Preview
@Composable
fun ModelsLayoutPreview() {
    ColumnPreview {
        ModelsLayout(
            modifier = Modifier.wrapContentHeight(),
            models = SDModel.mock()
        )
    }
}