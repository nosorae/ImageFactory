package com.yessorae.imagefactory.ui.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yessorae.imagefactory.model.PromptChip
import com.yessorae.imagefactory.model.mock
import com.yessorae.imagefactory.ui.item.PromptChip
import com.yessorae.imagefactory.ui.item.model.Chip
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.ui.util.compose.ColumnPreview
import com.yessorae.imagefactory.ui.util.compose.UiConfig

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PromptOptionLayout(
    modifier: Modifier = Modifier,
    prompts: List<Chip>,
    onPromptClick: (Chip) -> Unit = {}
) {
    val state = rememberLazyStaggeredGridState()
    LazyHorizontalStaggeredGrid(
        rows = StaggeredGridCells.Fixed(UiConfig.PROMPT_CAROUSEL_ROW),
        modifier = modifier.fillMaxWidth(),
        state = state,
        contentPadding = Dimen.carousel_padding_values,
        verticalArrangement = Arrangement.spacedBy(Dimen.space_8),
        horizontalItemSpacing = Dimen.space_4
    ) {
        itemsIndexed(
            items = prompts,
        ) { _, item ->
            PromptChip(
                model = item,
                onClick = { onPromptClick(item) }
            )
        }
    }
}

@Preview(heightDp = 300)
@Composable
fun PromptOptionLayoutPreview() {
    ColumnPreview {
        Box(modifier = Modifier.height(120.dp)) { // 높이 제한하지 않으면 크래시 발생
            PromptOptionLayout(
                prompts = PromptChip.mock()
            )
        }
    }
}
