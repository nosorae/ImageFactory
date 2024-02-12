package com.yessorae.presentation.ui.components.layout.option

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.domain.model.parameter.SDModel
import com.yessorae.presentation.ui.components.item.ModelCover
import com.yessorae.presentation.ui.components.item.ModelCoverPlaceholder
import com.yessorae.presentation.ui.screen.main.tti.model.ModelOption
import com.yessorae.presentation.ui.screen.main.tti.model.asOption
import com.yessorae.presentation.ui.theme.Dimen
import com.yessorae.presentation.util.compose.ColumnPreview

@Composable
fun ModelsLayout(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    models: () -> List<ModelOption>,
    loading: Boolean = false,
    onClick: (ModelOption) -> Unit = {}
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
            itemsIndexed(
                items = models(),
                contentType = { _, _ ->
                    ModelOption::class.java
                },
                key = { _, item ->
                    item.model.id
                }
            ) { _, item ->
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
    val list = remember {
        { SDModel.mock().map(SDModel::asOption) }
    }
    ColumnPreview {
        ModelsLayout(
            modifier = Modifier.wrapContentHeight(),
            models = list
        )
    }
}
