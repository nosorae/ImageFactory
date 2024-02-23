package com.yessorae.presentation.ui.screen.main.inpainting

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.components.dialog.ConfirmDialog
import com.yessorae.presentation.ui.components.dialog.FullModelOptionBottomSheet
import com.yessorae.presentation.ui.components.dialog.ImageAddOptionBottomSheet
import com.yessorae.presentation.ui.components.dialog.InputDialog
import com.yessorae.presentation.ui.components.item.ActionButton
import com.yessorae.presentation.ui.components.item.OptionTitle
import com.yessorae.presentation.ui.components.item.OptionTitleWithMore
import com.yessorae.presentation.ui.components.layout.option.ModelsLayout
import com.yessorae.presentation.ui.components.layout.option.NaturalNumberSliderOptionLayout
import com.yessorae.presentation.ui.components.layout.option.PromptOptionLayout
import com.yessorae.presentation.ui.screen.main.inpainting.model.InpaintingDialogState
import com.yessorae.presentation.ui.screen.main.inpainting.model.InPaintingUiState
import com.yessorae.presentation.ui.screen.main.tti.model.PromptOption
import com.yessorae.presentation.ui.screen.main.tti.model.SDModelOption
import com.yessorae.presentation.ui.screen.main.tti.model.TxtToImgDialog
import com.yessorae.presentation.ui.theme.Dimen
import com.yessorae.presentation.util.compose.Margin
import com.yessorae.presentation.util.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun getActivity() = LocalContext.current as ComponentActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InPaintingScreen(
    viewModel: InpaintingViewModel = hiltViewModel(),
    onNavOutEvent: (route: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()
    val positivePromptOptions by viewModel.positivePromptOptions.collectAsState()
    val negativePromptOptions by viewModel.negativePromptOptions.collectAsState()
    val guidanceScale by viewModel.guidanceScale.collectAsState()
    val featuredSDModelOptions by viewModel.featuredSdModelOptions.collectAsState()
    val sdModels by viewModel.allSDModelOptions.collectAsState()

    val context = LocalContext.current

    val takePictureLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                viewModel.completeTakePicture(bitmap = it)
            }
        }

    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                viewModel.completePickImage(uri = it)
            }
        }

    LaunchedEffect(key1 = Unit) {
        launch {
            viewModel.toast.collectLatest { message ->
                context.showToast(text = message)
            }
        }

        launch {
            viewModel.navigationEvent.collectLatest { route ->
                onNavOutEvent(route)
            }
        }

        launch {
            viewModel.takePicture.collectLatest {
                takePictureLauncher.launch(null)
            }
        }

        launch {
            viewModel.pickImage.collectLatest {
                pickImageLauncher.launch("image/*")
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.common_title_inpainting)
                    )
                },
                actions = {
                    // do nothing
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            when (val state = uiState) {
                is InPaintingUiState.Initial -> {
                    EmptyImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clickable {
                                viewModel.clickAddImage()
                            }
                    )
                }

                is InPaintingUiState.MaskedImage -> {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            Image(
                                bitmap = state.initialBitmap.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(Dimen.side_padding),
                                contentScale = ContentScale.FillWidth
                            )
                            Image(
                                bitmap = state.maskedBitmap.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(Dimen.side_padding),
                                contentScale = ContentScale.FillWidth
                            )
                        }

                        Margin(margin = Dimen.space_12)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Dimen.side_padding)
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            state.segmentationLabel.forEach { label ->
                                TextButton(onClick = { viewModel.clickSegmentationLabel(label = label) }) {
                                    Text(text = stringResource(id = label.resId))
                                }
                            }
                            Margin(margin = Dimen.space_24)
                        }

                        // positive prompt
                        OptionTitleWithMore(
                            text = stringResource(R.string.common_section_title_positive_prompt),
                            trailingText = stringResource(R.string.common_section_button_custom_prompt),
                            onClickMore = viewModel::clickToAddPositivePrompt
                        )
                        PromptOptionLayout(
                            prompts = { positivePromptOptions },
                            onClick = viewModel::clickPositivePrompt,
                            onLongClick = viewModel::longClickPrompt
                        )

                        // negative prompt
                        OptionTitleWithMore(
                            text = stringResource(R.string.common_section_title_negative_prompt),
                            trailingText = stringResource(R.string.common_section_button_custom_prompt),
                            onClickMore = viewModel::clickToAddNegativePrompt
                        )
                        PromptOptionLayout(
                            prompts = { negativePromptOptions },
                            onClick = viewModel::clickNegativePrompt,
                            onLongClick = viewModel::longClickPrompt
                        )

                        // prompt strength
                        OptionTitle(
                            text = stringResource(
                                R.string.common_section_title_prompt_strength,
                                guidanceScale
                            )
                        )
                        NaturalNumberSliderOptionLayout(
                            value = guidanceScale,
                            onValueChange = viewModel::changePromptStrength,
                            valueRange = 1..20
                        )

                        // stable diffusion model
                        OptionTitleWithMore(
                            text = stringResource(R.string.common_section_title_model),
                            trailingText = stringResource(R.string.common_button_see_more),
                            onClickMore = viewModel::clickMoreSDModel
                        )

                        ModelsLayout(
                            models = { featuredSDModelOptions },
                            onClick = { option ->
                                viewModel.clickFeaturedSDModel(
                                    clickedOption = option as SDModelOption
                                )
                            }
                        )

                        ActionButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Dimen.side_padding)
                                .padding(bottom = Dimen.space_16),
                            text = stringResource(R.string.common_button_generate_image),
                            onClick = viewModel::inPaintImage
                        )
                    }
                }

                else -> {
                    // do nothing
                }
            }

        }
    }

    when (val state = dialogState) {
        is InpaintingDialogState.ImageAddMethod -> {
            ImageAddOptionBottomSheet(
                onCancelDialog = viewModel::cancelDialog,
                onSelectPickImage = viewModel::clickPickImage,
                onSelectTakePicture = viewModel::clickTakePicture
            )
        }

        is InpaintingDialogState.PositivePromptAddition -> {
            InputDialog(
                onDismissRequest = viewModel::cancelDialog,
                onClickAddButton = viewModel::clickAddPositivePrompt
            )
        }

        is InpaintingDialogState.PromptDeletionConfirmation -> {
            ConfirmDialog(
                title = state.title,
                onClickConfirm = {
                    viewModel.clickDeletePrompt(state.promptOption)
                },
                onCancel = viewModel::cancelDialog
            )
        }

        is InpaintingDialogState.NegativePromptOptionAddition -> {
            InputDialog(
                onDismissRequest = viewModel::cancelDialog,
                onClickAddButton = viewModel::clickAddNegativePrompt
            )
        }

        is InpaintingDialogState.MoreSDModelBottomSheet -> {
            FullModelOptionBottomSheet(
                title = stringResource(R.string.common_section_title_model),
                options = sdModels,
                onCancelDialog = viewModel::cancelDialog,
                onSelectModelOption = {
                    viewModel.clickMoreSDModel(it as SDModelOption)
                }
            )
        }

        else -> {
            // do nothing
        }
    }


}