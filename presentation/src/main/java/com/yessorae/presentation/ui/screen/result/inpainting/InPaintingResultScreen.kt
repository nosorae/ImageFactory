package com.yessorae.presentation.ui.screen.result.inpainting

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.components.item.IconWithText
import com.yessorae.presentation.ui.components.layout.ImageCompareLayout
import com.yessorae.presentation.ui.components.layout.StableDiffusionLoadingLayout
import com.yessorae.presentation.ui.components.layout.UpscaleLoadingLayout
import com.yessorae.presentation.ui.screen.result.ResultBaseScreen
import com.yessorae.presentation.ui.screen.result.inpainting.model.InPaintingResultScreenState
import com.yessorae.presentation.ui.theme.Dimen
import com.yessorae.presentation.util.downloadImage
import com.yessorae.presentation.util.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InPaintingResultScreen(
    viewModel: InPaintingResultViewModel = hiltViewModel(),
    onNavEvent: (route: String) -> Unit,
    onBackEvent: () -> Unit
) {
    val context = LocalContext.current
    val screenState by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        launch {
            viewModel.toast.collectLatest { message ->
                context.showToast(text = message)
            }
        }

        launch(Dispatchers.IO) {
            viewModel.saveImageEvent.collectLatest { url ->
                try {
                    context.downloadImage(url = url)
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
            is InPaintingResultScreenState.Initial -> {
                // do nothing
            }

            is InPaintingResultScreenState.Loading -> {
                StableDiffusionLoadingLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValue)
                )
            }

            is InPaintingResultScreenState.SdSuccess -> {
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

            is InPaintingResultScreenState.UpscaleLoading -> {
                UpscaleLoadingLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValue)
                )
            }

            is InPaintingResultScreenState.UpscaleSuccess -> {
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

            is InPaintingResultScreenState.Processing -> {
                ProcessingScreen(
                    state = state,
                    onClickBackState = {
                        viewModel.onClickBack()
                    }
                )
            }

            is InPaintingResultScreenState.Error -> {
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
    state: InPaintingResultScreenState.SdSuccess,
    onClickRetry: (state: InPaintingResultScreenState.SdSuccess) -> Unit,
    onClickSave: (state: InPaintingResultScreenState.SdSuccess) -> Unit,
    onClickUpscale: (state: InPaintingResultScreenState.SdSuccess, bitmap: Bitmap?) -> Unit
) {
    val imageUrl = state.sdResult.firstImgUrl
    val context = LocalContext.current

    val sdResultImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .crossfade(true)
            .data(imageUrl)
            .build()
    )

    // TODO:: SR-N ViewModel 로 옮기기
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

    ResultBaseScreen(
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
    state: InPaintingResultScreenState.UpscaleSuccess,
    onClickRetry: (state: InPaintingResultScreenState.UpscaleSuccess) -> Unit,
    onClickSave: (state: InPaintingResultScreenState.UpscaleSuccess) -> Unit
) {
    val beforeImgUrl = state.beforeImageUrl
    ResultBaseScreen(
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
    state: InPaintingResultScreenState.Processing,
    onClickBackState: () -> Unit
) {
    ResultBaseScreen(
        backgroundImageUrl = null,
        imageContent = {
            Text(
                text = state.message,
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
    state: InPaintingResultScreenState.Error,
    onClickBackState: (InPaintingResultScreenState) -> Unit
) {
    val backState = state.backState
    ResultBaseScreen(
        backgroundImageUrl = when (backState) {
            is InPaintingResultScreenState.SdSuccess -> {
                backState.sdResult.firstImgUrl
            }

            is InPaintingResultScreenState.UpscaleLoading -> {
                backState.sdResult.firstImgUrl
            }

            is InPaintingResultScreenState.UpscaleSuccess -> {
                backState.sdResult.firstImgUrl
            }

            else -> {
                null
            }
        },
        imageContent = {
            Text(
                text = state.cause,
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
                text = stringResource(state.actionType.text),
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
