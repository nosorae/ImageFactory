package com.yessorae.imagefactory.ui.components.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yessorae.imagefactory.model.PromptOption
import com.yessorae.imagefactory.model.mock
import com.yessorae.imagefactory.ui.components.item.PromptChip
import com.yessorae.imagefactory.ui.components.item.model.Option
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.ui.theme.Transparent
import com.yessorae.imagefactory.ui.util.compose.ColumnPreview
import com.yessorae.imagefactory.ui.util.compose.UiConfig

/**
 * TODO
 * 칩선택된 거 앞으로 보내기
 * 커스텀 프롬프트 추가 버튼
 * 커스텀 프롬프트 추가할 때 ,로 구분하면 한 번에 여러 개 입력 가능
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PromptOptionLayout(
    modifier: Modifier = Modifier,
    prompts: List<Option>,
    onPromptClick: (Option) -> Unit = {}
) {
    val state = rememberLazyStaggeredGridState()

    LazyHorizontalStaggeredGrid(
        rows = StaggeredGridCells.Fixed(UiConfig.PROMPT_CAROUSEL_ROW),
        modifier = modifier.height(120.dp).fillMaxWidth(),
        state = state,
        contentPadding = Dimen.carousel_padding_values,
        verticalArrangement = Arrangement.spacedBy(Dimen.space_8),
        horizontalItemSpacing = Dimen.space_4
    ) {
        itemsIndexed(
            items = prompts
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
                prompts = PromptOption.mock()
            )
        }
    }
}
