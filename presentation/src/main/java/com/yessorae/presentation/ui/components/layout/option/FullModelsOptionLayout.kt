package com.yessorae.presentation.ui.components.layout.option

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yessorae.domain.model.parameter.SDModel
import com.yessorae.presentation.ui.components.item.GridModelCover
import com.yessorae.presentation.ui.screen.main.tti.model.ModelOption
import com.yessorae.presentation.ui.screen.main.tti.model.SDModelOption
import com.yessorae.presentation.ui.theme.Dimen
import com.yessorae.presentation.util.compose.ThemePreview
import com.yessorae.presentation.util.compose.UiConstants
import com.yessorae.presentation.util.compose.getScreenDp

@Composable
fun FullModelsLayout(
    modifier: Modifier = Modifier,
    models: List<ModelOption>,
    onClick: (ModelOption) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = UiConstants.MODEL_LIST_COLUMN),
        modifier = modifier.fillMaxWidth(),
        contentPadding = Dimen.grid_padding_values,
        horizontalArrangement = Arrangement.spacedBy(Dimen.space_4)
    ) {
        itemsIndexed(items = models) { _, item ->
            GridModelCover(
                model = item,
                onClick = { onClick(item) },
                modifier = Modifier.padding(top = Dimen.space_4, bottom = Dimen.space_12)
            )
        }
    }
}

@Preview
@Composable
fun FullModelsLayoutPreview() {
    ThemePreview(modifier = Modifier.width(getScreenDp().width)) {
        FullModelsLayout(
            models = SDModel.mock().mapIndexed { index, model ->
                SDModelOption(
                    selected = index % 3 == 0,
                    model = model
                )
            }
        )
    }
}
