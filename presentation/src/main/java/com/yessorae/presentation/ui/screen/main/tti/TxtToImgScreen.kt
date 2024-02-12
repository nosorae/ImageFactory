package com.yessorae.presentation.ui.screen.main.tti

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.yessorae.domain.util.stepCountRange
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.components.dialog.ConfirmDialog
import com.yessorae.presentation.ui.components.dialog.FullModelOptionBottomSheet
import com.yessorae.presentation.ui.components.dialog.InputDialog
import com.yessorae.presentation.ui.components.item.ActionButton
import com.yessorae.presentation.ui.components.item.OptionTitle
import com.yessorae.presentation.ui.components.item.OptionTitleWithMore
import com.yessorae.presentation.ui.components.layout.LoadingLayout
import com.yessorae.presentation.ui.components.layout.option.ModelsLayout
import com.yessorae.presentation.ui.components.layout.option.NaturalNumberSliderOptionLayout
import com.yessorae.presentation.ui.components.layout.option.PromptOptionLayout
import com.yessorae.presentation.ui.components.layout.option.RadioOptionLayout
import com.yessorae.presentation.ui.components.layout.option.ZeroToOneSliderOptionLayout
import com.yessorae.presentation.ui.components.layout.option.roundToOneDecimalPlace
import com.yessorae.presentation.ui.screen.main.tti.model.EmbeddingsModelOption
import com.yessorae.presentation.ui.screen.main.tti.model.LoRaModelOption
import com.yessorae.presentation.ui.screen.main.tti.model.PromptOption
import com.yessorae.presentation.ui.screen.main.tti.model.SDModelOption
import com.yessorae.presentation.ui.screen.main.tti.model.SchedulerOption
import com.yessorae.presentation.ui.screen.main.tti.model.TxtToImgDialog
import com.yessorae.presentation.ui.theme.Dimen
import com.yessorae.presentation.util.compose.Padding
import com.yessorae.presentation.util.compose.rememberDebouncedEvent
import com.yessorae.presentation.util.getSettingsLocale
import com.yessorae.presentation.util.redirectToWebBrowser
import com.yessorae.presentation.util.showToast
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TxtToImgScreen(
    viewModel: TxtToImgViewModel = hiltViewModel(),
    onNavOutEvent: (route: String) -> Unit
) {
    val positivePromptOptions by viewModel.positivePromptOptions.collectAsState()
    val negativePromptOptions by viewModel.negativePromptOptions.collectAsState()
    val featuredSDModelOptions by viewModel.featuredSdModelOptions.collectAsState()
    val featuredLoRaModelOptions by viewModel.featuredLoRaModelOptions.collectAsState()
    val featuredSelectedLoRaModelOptions by viewModel.selectedFeaturdLoRaModelOptions.collectAsState()
    val featuredEmbeddingsModelOptions by viewModel.featuredEmbeddingsModelOptions.collectAsState()
    val schedulerOptions by viewModel.schedulerOptions.collectAsState()
    val stepCount by viewModel.stepCount.collectAsState()
    val guidanceScale by viewModel.guidanceScale.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val dialogState by viewModel.dialog.collectAsState()

    val singleEvent = rememberDebouncedEvent()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        launch {
            viewModel.toast.collectLatest { message ->
                context.showToast(text = message)
            }
        }

        launch {
            viewModel.redirectToWebBrowserEvent.collectLatest { link ->
                context.redirectToWebBrowser(
                    link = link,
                    onActivityNotFoundException = {
                        viewModel.onFailRedirectToWebBrowser()
                    }
                )
            }
        }

        launch {
            viewModel.navigationEvent.collectLatest { route ->
                onNavOutEvent(route)
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.common_title_txt_to_img)
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            singleEvent.processEvent {
                                viewModel.clickHelp(context.getSettingsLocale())
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.HelpOutline, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            ActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimen.side_padding)
                    .padding(bottom = Dimen.space_16),
                text = stringResource(R.string.common_button_generate_image)
            ) {
                viewModel.clickGenerateImage()
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = paddingValues.calculateTopPadding()),
        ) {
            // positive prompt
            OptionTitleWithMore(
                text = stringResource(R.string.common_section_title_positive_prompt),
                trailingText = stringResource(R.string.common_section_button_custom_prompt),
                onClickMore = {
                    viewModel.clickToAddPositivePrompt()
                }
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
                },
            )

            // loRa
            OptionTitleWithMore(
                text = stringResource(R.string.common_section_title_lora),
                trailingText = stringResource(R.string.common_button_see_more),
                onClickMore = viewModel::clickMoreLoRaModel
            )
            ModelsLayout(
                models = { featuredLoRaModelOptions },
                onClick = { option ->
                    viewModel.clickLoRaModel(
                        clickedOption = option as LoRaModelOption
                    )
                }
            )
            featuredSelectedLoRaModelOptions.forEach { loRaModelOption ->
                OptionTitle(
                    text = stringResource(
                        R.string.common_section_title_lora_strength,
                        loRaModelOption.model.displayName,
                        loRaModelOption.model.strength.roundToOneDecimalPlace().toString()
                    )
                )
                ZeroToOneSliderOptionLayout(
                    value = loRaModelOption.model.strength,
                    onValueChange = { strength ->
                        viewModel.changeLoRaModelStrength(
                            clickedOption = loRaModelOption,
                            strength = strength
                        )
                    }
                )
            }

            // embeddings
            OptionTitleWithMore(
                text = stringResource(R.string.common_section_title_embeddings),
                trailingText = stringResource(R.string.common_button_see_more),
                onClickMore = viewModel::clickMoreEmbeddingsModel
            )
            ModelsLayout(
                models = { featuredEmbeddingsModelOptions },
                onClick = { option ->
                    viewModel.clickEmbeddingsModel(
                        clickedOption = option as EmbeddingsModelOption
                    )
                }
            )

            // step count
            OptionTitle(
                text = stringResource(
                    R.string.common_section_title_step_count,
                    stepCount
                )
            )
            NaturalNumberSliderOptionLayout(
                value = stepCount,
                onValueChange = viewModel::changeStepCount,
                valueRange = stepCountRange
            )


            // scheduler
            OptionTitle(
                text = stringResource(R.string.common_section_title_scheduler)
            )
            RadioOptionLayout(
                options = { schedulerOptions },
                onClick = { option ->
                    viewModel.changeScheduler(
                        clickedOption = (option as SchedulerOption)
                    )
                }
            )

            Padding(margin = Dimen.lazy_col_bottom_padding)
        }
    }

    TxtToImgDialogScreen(
        dialogState = dialogState,
        sdModelsState = viewModel.allSDModelOptions,
        loRaModelsState = viewModel.allLoRaModelOptions,
        embeddingsModelsState = viewModel.allEmbeddingsModelOptions,
        onAddPositivePrompt = viewModel::clickAddPositivePrompt,
        onAddNegativePrompt = viewModel::clickAddNegativePrompt,
        onSelectSDModel = viewModel::clickMoreSDModel,
        onSelectLoRaModel = viewModel::clickMoreLoRaModel,
        onSelectEmbeddingsModelOption = viewModel::clickMoreEmbeddingsModel,
        onClickDeletePrompt = viewModel::clickDeletePrompt,
        onCancelDialog = viewModel::clickCancelDialog
    )

    if (loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            LoadingLayout(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(
                        Dimen.medium_icon_size
                    )
            )
        }
    }
}

@Composable
fun TxtToImgDialogScreen(
    dialogState: TxtToImgDialog,
    sdModelsState: StateFlow<List<SDModelOption>>,
    loRaModelsState: StateFlow<List<LoRaModelOption>>,
    embeddingsModelsState: StateFlow<List<EmbeddingsModelOption>>,
    onAddPositivePrompt: (String) -> Unit,
    onAddNegativePrompt: (String) -> Unit,
    onSelectSDModel: (SDModelOption) -> Unit,
    onSelectLoRaModel: (LoRaModelOption) -> Unit,
    onSelectEmbeddingsModelOption: (EmbeddingsModelOption) -> Unit,
    onClickDeletePrompt: (PromptOption) -> Unit,
    onCancelDialog: () -> Unit
) {
    val sdModels by sdModelsState.collectAsState()
    val loRaModels by loRaModelsState.collectAsState()
    val embeddingsModels by embeddingsModelsState.collectAsState()

    when (dialogState) {
        is TxtToImgDialog.PositivePromptAddition -> {
            InputDialog(
                onDismissRequest = onCancelDialog,
                onClickAddButton = onAddPositivePrompt
            )
        }

        is TxtToImgDialog.NegativePromptOptionAddition -> {
            InputDialog(
                onDismissRequest = onCancelDialog,
                onClickAddButton = onAddNegativePrompt
            )
        }

        is TxtToImgDialog.MoreSDModelBottomSheet -> {
            FullModelOptionBottomSheet(
                title = stringResource(R.string.common_section_title_model),
                options = sdModels,
                onCancelDialog = onCancelDialog,
                onSelectModelOption = {
                    onSelectSDModel(it as SDModelOption)
                }
            )
        }

        is TxtToImgDialog.MoreLoRaModelBottomSheet -> {
            FullModelOptionBottomSheet(
                title = stringResource(R.string.common_section_title_model),
                options = loRaModels,
                onCancelDialog =onCancelDialog,
                onSelectModelOption = {
                    onSelectLoRaModel(it as LoRaModelOption)
                }
            )
        }

        is TxtToImgDialog.MoreEmbeddingsBottomSheet -> {
            FullModelOptionBottomSheet(
                title = stringResource(R.string.common_section_title_lora),
                options = embeddingsModels,
                onCancelDialog = onCancelDialog,
                onSelectModelOption = {
                    onSelectEmbeddingsModelOption(it as EmbeddingsModelOption)
                }
            )
        }

        is TxtToImgDialog.PromptDeletionConfirmation -> {
            ConfirmDialog(
                title = dialogState.title,
                onClickConfirm = {
                    onClickDeletePrompt(dialogState.promptOption)
                },
                onCancel = onCancelDialog
            )
        }

        else -> {
            // do nothing
        }
    }
}
