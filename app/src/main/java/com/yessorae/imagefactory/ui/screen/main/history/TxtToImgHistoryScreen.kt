package com.yessorae.imagefactory.ui.screen.main.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.yessorae.imagefactory.ui.components.item.common.ImageListItem
import com.yessorae.imagefactory.ui.components.layout.DEFAULT_IMAGE_LIST_COLUMN
import com.yessorae.imagefactory.ui.components.layout.DefaultLoadingLayout
import com.yessorae.imagefactory.ui.components.layout.LoadingLayout
import com.yessorae.imagefactory.ui.model.TxtToImgHistory
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.util.compose.Margin

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TxtToImgHistoryScreen(
    viewModel: TxtToImgHistoryViewModel = hiltViewModel()
) {
    val screenState by viewModel.state.collectAsState()
    val listState = rememberLazyStaggeredGridState()

    when (val state = screenState) {
        is TxtToImgHistoryScreenState.Loading -> {
            DefaultLoadingLayout()
        }

        is TxtToImgHistoryScreenState.View -> {
            HistoryListLayout(
                modifier = Modifier.fillMaxWidth(),
                histories = state.histories,
                listState = listState
            )
        }

        is TxtToImgHistoryScreenState.Edit -> {

        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HistoryListLayout(
    modifier: Modifier = Modifier,
    histories: List<TxtToImgHistory>,
    listState: LazyStaggeredGridState
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(DEFAULT_IMAGE_LIST_COLUMN),
        modifier = modifier,
        state = listState,
        contentPadding = Dimen.grid_padding_values,
        verticalItemSpacing = Dimen.space_4,
        horizontalArrangement = Arrangement.spacedBy(Dimen.space_8)
    ) {
        itemsIndexed(
            items = histories,
            contentType = { index, item ->
                TxtToImgHistory::class.java.simpleName
            }
        ) { index, item ->
            HistoryListItem(
                modifier = modifier.fillMaxWidth(),
                history = item
            )
        }
    }
}

@Composable
fun HistoryListItem(
    modifier: Modifier = Modifier,
    history: TxtToImgHistory
) {
    Column(modifier = modifier) {
        ImageListItem(model = history.result?.imageUrl, modifier = Modifier.fillMaxWidth())
        Margin(margin = Dimen.space_4)
        Text(
            text = "status ${history.result?.status}",
            maxLines = 2,
            overflow = TextOverflow.Clip
        )
    }

}