package com.yessorae.imagefactory.ui.components.layout.option

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.domain.model.option.SDModelOption
import com.yessorae.domain.model.option.mock
import com.yessorae.imagefactory.ui.components.item.ModelCover
import com.yessorae.imagefactory.ui.components.item.ModelCoverPlaceholder
import com.yessorae.domain.model.option.CoverOption
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.util.compose.ColumnPreview

@Composable
fun ModelsLayout(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    models: List<CoverOption>,
    loading: Boolean = true,
    onClick: (CoverOption) -> Unit = {}
) {
    LazyRow(
        modifier = modifier,
        state = state,
        contentPadding = Dimen.carousel_padding_values,
        horizontalArrangement = Arrangement.spacedBy(Dimen.space_4),
        userScrollEnabled = loading.not()
    ) {
        if (loading) {
            repeat(6) {
                item {
                    ModelCoverPlaceholder()
                }
            }
        } else {
            itemsIndexed(items = models) { _, item ->
                ModelCover(
                    model = item,
                    onClick = { onClick(item) }
                )
            }
        }
    }
}

@Preview
@Composable
fun ModelsLayoutPreview() {
    ColumnPreview {
        ModelsLayout(
            modifier = Modifier.wrapContentHeight(),
            models = SDModelOption.mock()
        )
    }
}
