package com.yessorae.imagefactory.ui.components.layout

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
import com.yessorae.imagefactory.model.SDModelOption
import com.yessorae.imagefactory.model.mock
import com.yessorae.imagefactory.ui.components.item.GridModelCover
import com.yessorae.imagefactory.ui.components.item.model.CoverOption
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.ui.util.compose.ThemePreview
import com.yessorae.imagefactory.ui.util.compose.UiConfig
import com.yessorae.imagefactory.ui.util.compose.getScreenDp

@Composable
fun FullModelsLayout(
    modifier: Modifier = Modifier,
    models: List<CoverOption>,
    onClick: (CoverOption) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = UiConfig.MODEL_LIST_COLUMN),
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
            models = SDModelOption.mock()
        )
    }
}

