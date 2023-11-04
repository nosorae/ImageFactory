package com.yessorae.imagefactory.ui.screen.result

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.ui.components.item.IconWithText
import com.yessorae.imagefactory.ui.components.layout.ImageCompareLayout
import com.yessorae.imagefactory.ui.components.layout.StableDiffusionLoadingLayout
import com.yessorae.imagefactory.ui.components.layout.UpscaleLoadingLayout
import com.yessorae.imagefactory.ui.screen.result.model.TxtToImgResultScreenState
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.util.downloadImageByUrl
import com.yessorae.imagefactory.util.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TxtToImgResultScreen(
    viewModel: TxtToImgResultViewModel = hiltViewModel(),
    onNavEvent: (route: String) -> Unit,
    onBackEvent: () -> Unit
) {
    val context = LocalContext.current
    val screenState by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        launch {
            viewModel.toast.collectLatest { message ->
                context.showToast(stringModel = message)
            }
        }

        launch(Dispatchers.IO) {
            viewModel.saveImageEvent.collectLatest { url ->
                try {
                    context.downloadImageByUrl(url = url)
                    viewModel.onSaveComplete()
                } catch (e: Exception) {
                    viewModel.onSaveFailed(error = e)
                }
            }
        }

        launch {
            viewModel.navigationEvent.collectLatest { route ->
                onNavEvent(route)
            }
        }

        launch {
            viewModel.backNavigationEvent.collectLatest {
                onBackEvent()
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // do nothing
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackEvent
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.DarkGray,
                                Color.Transparent
                            )
                        )
                    ),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                    actionIconContentColor = Color.Transparent
                )
            )
        }
    ) { paddingValue ->
        when (val state = screenState) {
            is TxtToImgResultScreenState.Initial -> {
                // do nothing
            }

            is TxtToImgResultScreenState.Loading -> {
                StableDiffusionLoadingLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValue)
                )
            }

            is TxtToImgResultScreenState.SdSuccess -> {
                SdSuccessScreen(
                    state = state,
                    onClickRetry = { success ->
                        viewModel.onClickRetry(currentState = success)
                    },
                    onClickSave = { success ->
                        viewModel.onClickSave(currentState = success)
                    },
                    onClickUpscale = { success, bitmap ->
                        viewModel.onClickUpscale(
                            currentState = success,
                            sdResultBitmap = bitmap
                        )
                    }
                )
            }

            is TxtToImgResultScreenState.UpscaleLoading -> {
                UpscaleLoadingLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValue)
                )
            }

            is TxtToImgResultScreenState.UpscaleSuccess -> {
                UpscaleSuccessScreen(
                    state = state,
                    onClickRetry = { success ->
                        viewModel.onClickRetry(currentState = success)
                    },
                    onClickSave = { success ->
                        viewModel.onClickSave(currentState = success)
                    }
                )
            }

            is TxtToImgResultScreenState.Processing -> {
                ProcessingScreen(
                    state = state,
                    onClickBackState = {
                        viewModel.onClickBack()
                    }
                )
            }

            is TxtToImgResultScreenState.Error -> {
                ErrorScreen(
                    state = state,
                    onClickBackState = { backState ->
                        viewModel.onClickBackFromError(backState = backState)
                    }
                )
            }
        }
    }
}

@Composable
fun SdSuccessScreen(
    state: TxtToImgResultScreenState.SdSuccess,
    onClickRetry: (state: TxtToImgResultScreenState.SdSuccess) -> Unit,
    onClickSave: (state: TxtToImgResultScreenState.SdSuccess) -> Unit,
    onClickUpscale: (state: TxtToImgResultScreenState.SdSuccess, bitmap: Bitmap?) -> Unit
) {
    val imageUrl = state.sdResult.imageUrl
    val context = LocalContext.current

    val sdResultImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .crossfade(true)
            .data(imageUrl)
            .build()
    )

    var bitmapForUpscale: Bitmap? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = Unit) {
        launch {
            snapshotFlow {
                sdResultImageState.state
            }.collectLatest { state ->
                if (state is AsyncImagePainter.State.Success) {
                    bitmapForUpscale = state.result.drawable.toBitmap()
                }
            }
        }
    }

    BaseScreen(
        backgroundImageUrl = imageUrl,
        imageContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Dimen.space_16)
                    .aspectRatio(state.ratio)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                Image(
                    painter = sdResultImageState,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )

                when (val imageLoadState = sdResultImageState.state) {
                    is AsyncImagePainter.State.Loading -> {
                        Text(
                            text = "loading",
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center
                        )
                    }

                    is AsyncImagePainter.State.Error -> {
                        Text(
                            text = imageLoadState.result.toString(),
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center
                        )
                    }

                    else -> {
                        // do nothing
                    }
                }
            }
        },
        optionContent = {
            IconWithText(
                imageVector = Icons.Default.Replay,
                text = stringResource(id = R.string.result_dialog_option_retry),
                onClick = {
                    onClickRetry(state)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = Dimen.space_24)

            )
            IconWithText(
                imageVector = Icons.Default.SaveAlt,
                text = stringResource(id = R.string.result_dialog_option_save),
                onClick = {
                    onClickSave(state)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = Dimen.space_24)
            )
            IconWithText(
                imageVector = Icons.Default.AutoAwesome,
                text = stringResource(id = R.string.result_dialog_option_upscale),
                onClick = {
                    onClickUpscale(state, bitmapForUpscale)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = Dimen.space_24)
            )
        }
    )
}

@Composable
fun UpscaleSuccessScreen(
    state: TxtToImgResultScreenState.UpscaleSuccess,
    onClickRetry: (state: TxtToImgResultScreenState.UpscaleSuccess) -> Unit,
    onClickSave: (state: TxtToImgResultScreenState.UpscaleSuccess) -> Unit
) {
    val beforeImgUrl = state.beforeImageUrl
    BaseScreen(
        backgroundImageUrl = beforeImgUrl,
        imageContent = {
            ImageCompareLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimen.space_16)
                    .aspectRatio(state.ratio)
                    .clip(MaterialTheme.shapes.medium),
                before = beforeImgUrl,
                after = state.afterImageUrl,
                onAfterLoadingComplete = {
                    // do nothing
                }
            )
        },
        optionContent = {
            IconWithText(
                imageVector = Icons.Default.Replay,
                text = stringResource(id = R.string.result_dialog_option_retry),
                onClick = {
                    onClickRetry(state)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = Dimen.space_24)

            )
            IconWithText(
                imageVector = Icons.Default.SaveAlt,
                text = stringResource(id = R.string.result_dialog_option_save),
                onClick = {
                    onClickSave(state)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = Dimen.space_24)
            )
        }
    )
}

@Composable
private fun ProcessingScreen(
    state: TxtToImgResultScreenState.Processing,
    onClickBackState: () -> Unit
) {
    BaseScreen(
        backgroundImageUrl = null,
        imageContent = {
            Text(
                text = state.message.getValue(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimen.space_16),
                maxLines = 7,
                overflow = TextOverflow.Clip
            )
        },
        optionContent = {
            IconWithText(
                imageVector = Icons.Default.Replay,
                text = stringResource(id = R.string.result_dialog_option_back),
                onClick = {
                    onClickBackState()
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = Dimen.space_24)

            )
        }
    )
}

@Composable
private fun ErrorScreen(
    state: TxtToImgResultScreenState.Error,
    onClickBackState: (TxtToImgResultScreenState) -> Unit
) {
    val backState = state.backState
    BaseScreen(
        backgroundImageUrl = when (backState) {
            is TxtToImgResultScreenState.SdSuccess -> {
                backState.sdResult.imageUrl
            }

            is TxtToImgResultScreenState.UpscaleLoading -> {
                backState.sdResult.imageUrl
            }

            is TxtToImgResultScreenState.UpscaleSuccess -> {
                backState.sdResult.imageUrl
            }

            else -> {
                null
            }
        },
        imageContent = {
            Text(
                text = state.cause.getValue(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimen.space_16),
                maxLines = 7,
                overflow = TextOverflow.Clip
            )
        },
        optionContent = {
            IconWithText(
                imageVector = state.actionType.imageVector,
                text = state.actionType.text.getValue(),
                onClick = {
                    onClickBackState(backState)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = Dimen.space_24)

            )
        }
    )
}

@Composable
private fun BaseScreen(
    backgroundImageUrl: String?,
    imageContent: @Composable () -> Unit,
    optionContent: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TxtToImgResultBackground(imageUrl = backgroundImageUrl)

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                imageContent()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.DarkGray
                            )
                        )
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                optionContent()
            }
        }
    }
}

@Composable
fun TxtToImgResultBackground(imageUrl: String?) {
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .build(),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .blur(
                radiusX = 10.dp,
                radiusY = 10.dp,
                edgeTreatment = BlurredEdgeTreatment.Unbounded
            ),
        contentScale = ContentScale.Crop
    )
}
