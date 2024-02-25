package com.yessorae.presentation.ui.components.layout.option

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yessorae.domain.model.parameter.Prompt
import com.yessorae.presentation.ui.components.item.PromptChip
import com.yessorae.presentation.ui.screen.main.tti.model.PromptOption
import com.yessorae.presentation.ui.screen.main.tti.model.asOption
import com.yessorae.presentation.ui.theme.Dimen
import com.yessorae.presentation.util.compose.ColumnPreview
import com.yessorae.presentation.util.compose.Margin

/**
 * TODO
 * 칩선택된 거 앞으로 보내기
 * 커스텀 프롬프트 추가 버튼
 * 커스텀 프롬프트 추가할 때 ,로 구분하면 한 번에 여러 개 입력 가능
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PromptOptionLayout(
    modifier: Modifier = Modifier,
    prompts: () -> List<PromptOption>,
    onClick: (PromptOption) -> Unit = {},
    onLongClick: (PromptOption) -> Unit = {}
) {
    val state = rememberLazyStaggeredGridState()

    LazyHorizontalStaggeredGrid(
        rows = StaggeredGridCells.Adaptive(24.dp),
        modifier = modifier
            .height(120.dp)
            .fillMaxWidth(),
        state = state,
        contentPadding = Dimen.carousel_padding_values,
        verticalArrangement = Arrangement.spacedBy(Dimen.space_8),
        horizontalItemSpacing = Dimen.space_4
    ) {
        itemsIndexed(
            items = prompts(),
            contentType = { _, _ ->
                PromptOption::class.java
            },
            key = { _, item ->
                item.prompt
            }
        ) { _, item ->
            PromptChip(
                model = item,
                onClick = { onClick(item) },
                onLongClick = { onLongClick(item) }
            )
        }
    }
}

@Preview(heightDp = 300)
@Composable
fun PromptOptionLayoutPreview() {
    val list = remember {
        { Prompt.mock().map(Prompt::asOption) }
    }
    ColumnPreview {
        Box(modifier = Modifier.height(120.dp)) { // 높이 제한하지 않으면 크래시 발생
            PromptOptionLayout(
                prompts = list
            )
        }
    }
}
