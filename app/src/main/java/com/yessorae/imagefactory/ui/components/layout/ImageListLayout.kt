package com.yessorae.imagefactory.ui.components.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yessorae.imagefactory.ui.theme.Dimen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageListLayout(
    modifier: Modifier = Modifier,
    content: LazyStaggeredGridScope.() -> Unit
) {
    val state = rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(DEFAULT_IMAGE_LIST_COLUMN),
        modifier = modifier,
        state = state,
        contentPadding = Dimen.grid_padding_values,
        verticalItemSpacing = Dimen.space_4,
        horizontalArrangement = Arrangement.spacedBy(Dimen.space_8)
    ) {
        content()
    }
}

const val DEFAULT_IMAGE_LIST_COLUMN = 2
