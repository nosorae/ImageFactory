package com.yessorae.imagefactory.ui.screen.main.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.yessorae.common.Constants
import com.yessorae.data.util.StableDiffusionConstants
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.ui.components.dialog.ConfirmDialog
import com.yessorae.imagefactory.ui.components.item.common.ImageListItem
import com.yessorae.imagefactory.ui.components.item.common.ImageLoadError
import com.yessorae.imagefactory.ui.components.layout.DEFAULT_IMAGE_LIST_COLUMN
import com.yessorae.imagefactory.ui.components.layout.DefaultLoadingLayout
import com.yessorae.imagefactory.ui.components.layout.EmptyListLayout
import com.yessorae.imagefactory.ui.model.TxtToImgHistory
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.util.ResString
import com.yessorae.imagefactory.util.compose.Margin
import com.yessorae.imagefactory.util.compose.debouncedClickable
import com.yessorae.imagefactory.util.compose.rememberDebouncedEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TxtToImgHistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    onNavOutEvent: (route: String) -> Unit
) {
    val screenState by viewModel.state.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()
    val listState = rememberLazyStaggeredGridState()

    LaunchedEffect(key1 = Unit) {
        launch {
            viewModel.navigationEvent.collectLatest { route ->
                onNavOutEvent(route)
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "History")
            })
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            when (val state = screenState) {
                is TxtToImgHistoryScreenState.Loading -> {
                    DefaultLoadingLayout()
                }

                is TxtToImgHistoryScreenState.View -> {
                    HistoryListLayout(
                        modifier = Modifier.fillMaxSize(),
                        histories = state.histories,
                        listState = listState,
                        onClickDelete = { history ->
                            viewModel.onClickDeleteTxtToImgHistory(
                                txtToImgHistory = history
                            )
                        },
                        onClickFetch = { history ->
                            viewModel.onClickFetch(
                                txtToImgHistory = history
                            )
                        },
                        onClickImage = { history ->
                            viewModel.onClickImage(
                                txtToImgHistory = history
                            )
                        }
                    )
                }
            }
        }
    }

    when (val state = dialogState) {
        is HistoryDialogState.Delete -> {
            ConfirmDialog(
                title = ResString(R.string.common_dialog_title_delete_history),
                body = ResString(R.string.common_dialog_body_delete_history),
                onCancel = viewModel::onCancelDialog,
                onClickConfirm = {
                    viewModel.onClickConfirmTxtToImgHistory(txtToImgHistory = state.txtToImgHistory)
                }
            )
        }

        is HistoryDialogState.None -> {
            // do nothing
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HistoryListLayout(
    modifier: Modifier = Modifier,
    histories: List<TxtToImgHistory>,
    listState: LazyStaggeredGridState,
    onClickDelete: (TxtToImgHistory) -> Unit,
    onClickFetch: (TxtToImgHistory) -> Unit,
    onClickImage: (TxtToImgHistory) -> Unit
) {
    if (histories.isEmpty()) {
        EmptyListLayout(
            text = ResString(R.string.history_list_empty)
        )
    } else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(DEFAULT_IMAGE_LIST_COLUMN),
            modifier = modifier,
            state = listState,
            contentPadding = Dimen.grid_padding_values,
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
                    history = item,
                    onClickDelete = {
                        onClickDelete(item)
                    },
                    onClickFetch = {
                        onClickFetch(item)
                    },
                    onClickImage = {
                        onClickImage(item)
                    }
                )
            }
        }
    }
}

@Composable
private fun HistoryListItem(
    modifier: Modifier = Modifier,
    history: TxtToImgHistory,
    onClickImage: () -> Unit,
    onClickDelete: () -> Unit,
    onClickFetch: () -> Unit
) {
    val singleEvent = rememberDebouncedEvent()
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(history.request.width / history.request.height.toFloat())
            ) {
                if (history.result?.status == StableDiffusionConstants.RESPONSE_PROCESSING && history.result.imageUrl == null) {
                    ImageLoadError(
                        modifier = Modifier
                            .fillMaxSize()
                            .debouncedClickable(
                                onClick = onClickFetch,
                                interval = Constants.MIN_FETCH_INTERVAL
                            ),
                        text = stringResource(id = R.string.history_cover_error_message)
                    )
                } else {
                    ImageListItem(
                        model = history.result?.imageUrl,
                        modifier = Modifier
                            .fillMaxSize()
                            .debouncedClickable(
                                onClick = onClickImage
                            )
                    )
                }
            }

            Margin(margin = Dimen.space_4)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = history.createdAt.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
            Margin(margin = Dimen.space_4)
        }

        IconButton(
            onClick = {
                singleEvent.processEvent {
                    onClickDelete()
                }
            },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = null
            )
        }
    }
}
