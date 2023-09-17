package com.yessorae.imagefactory.ui.screen.main.tti

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.model.EmbeddingsModelOption
import com.yessorae.imagefactory.model.LoRaModelOption
import com.yessorae.imagefactory.model.PromptOption
import com.yessorae.imagefactory.model.SDModelOption
import com.yessorae.imagefactory.model.SchedulerOption
import com.yessorae.imagefactory.ui.components.dialog.FullModelOptionBottomSheet
import com.yessorae.imagefactory.ui.components.dialog.InputDialog
import com.yessorae.imagefactory.ui.components.item.ActionButton
import com.yessorae.imagefactory.ui.components.item.OptionTitle
import com.yessorae.imagefactory.ui.components.item.OptionTitleWithMore
import com.yessorae.imagefactory.ui.components.layout.LoadingLayout
import com.yessorae.imagefactory.ui.components.layout.option.ModelsLayout
import com.yessorae.imagefactory.ui.components.layout.option.NaturalNumberSliderOptionLayout
import com.yessorae.imagefactory.ui.components.layout.option.OnOffOptionLayout
import com.yessorae.imagefactory.ui.components.layout.option.PromptOptionLayout
import com.yessorae.imagefactory.ui.components.layout.option.RadioOptionLayout
import com.yessorae.imagefactory.ui.components.layout.option.ZeroToOneSliderOptionLayout
import com.yessorae.imagefactory.ui.components.layout.option.roundToOneDecimalPlace
import com.yessorae.imagefactory.ui.screen.main.tti.model.MoreEmbeddingsBottomSheet
import com.yessorae.imagefactory.ui.screen.main.tti.model.MoreLoRaModelBottomSheet
import com.yessorae.imagefactory.ui.screen.main.tti.model.MoreSDModelBottomSheet
import com.yessorae.imagefactory.ui.screen.main.tti.model.NegativePromptOptionAdditionDialog
import com.yessorae.imagefactory.ui.screen.main.tti.model.PositivePromptAdditionDialog
import com.yessorae.imagefactory.ui.screen.main.tti.model.SeedChangeDialog
import com.yessorae.imagefactory.ui.screen.main.tti.model.TxtToImgDialogState
import com.yessorae.imagefactory.ui.screen.main.tti.model.TxtToImgOptionState
import com.yessorae.imagefactory.ui.theme.Dimen
import com.yessorae.imagefactory.util.ResString
import com.yessorae.imagefactory.util.TextString
import com.yessorae.imagefactory.util.getSettingsLocale
import com.yessorae.imagefactory.util.redirectToWebBrowser
import com.yessorae.imagefactory.util.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TxtToImgScreen(
    viewModel: TxtToImgViewModel = hiltViewModel(),
    onNavOutEvent: (route: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val requestModel = uiState.request

    val dialogState by viewModel.dialogEvent.collectAsState()

    val context = LocalContext.current

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(key1 = Unit) {
        launch {
            viewModel.toast.collectLatest { message ->
                context.showToast(stringModel = message)
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
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.common_title_txt_to_img)
                    )
                },
                actions = {
//                    IconButton(
//                        onClick = {
//                            // todo
//                        }
//                    ) {
//                        Icon(imageVector = Icons.Default.HelpOutline, contentDescription = null)
//                    }

                    IconButton(
                        onClick = {
                            viewModel.onClickHelp(context.getSettingsLocale())
                        }
                    ) {
                        Icon(imageVector = Icons.Default.HelpOutline, contentDescription = null)
                    }
                },
                scrollBehavior = scrollBehavior,
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
                viewModel.onClickGenerateImage()
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            contentPadding = PaddingValues(bottom = Dimen.lazy_col_bottom_padding)
        ) {
            item {
                OptionTitleWithMore(
                    text = ResString(R.string.common_section_title_positive_prompt),
                    trailingText = ResString(R.string.common_section_button_custom_prompt),
                    onClickMore = {
                        viewModel.onClickAddPositivePrompt()
                    }
                )
            }
            item {
                PromptOptionLayout(
                    prompts = requestModel.positivePromptOptions,
                    onPromptClick = { option ->
                        viewModel.onSelectPositivePrompt(option as PromptOption)
                    }
                )
            }

            item {
                OptionTitleWithMore(
                    text = ResString(R.string.common_section_title_negative_prompt),
                    trailingText = ResString(R.string.common_section_button_custom_prompt),
                    onClickMore = {
                        viewModel.onClickAddNegativePrompt()
                    }
                )
            }
            item {
                PromptOptionLayout(
                    prompts = requestModel.negativePromptOptions,
                    onPromptClick = { option ->
                        viewModel.onSelectNegativePrompt(option as PromptOption)
                    }
                )
            }

            item {
                OnOffOptionLayout(
                    text = ResString(R.string.common_section_title_prompt_enhancement),
                    checked = requestModel.enhancePrompt,
                    onCheckedChange = { enabled ->
                        viewModel.onChangeEnhancePrompt(
                            enabled = enabled
                        )
                    }
                )
            }

            // prompt strength
            item(
                contentType = "OptionTitle"
            ) {
                OptionTitle(
                    text = ResString(
                        R.string.common_section_title_prompt_strength,
                        requestModel.guidanceScale.toString()
                    )
                )
            }
            item {
                NaturalNumberSliderOptionLayout(
                    value = requestModel.guidanceScale,
                    onValueChange = { guidanceScale ->
                        viewModel.onChangePromptStrength(
                            guidanceScale = guidanceScale
                        )
                    },
                    valueRange = 1..20
                )
            }

            // model
            item(
                contentType = "OptionTitle"
            ) {
                OptionTitleWithMore(
                    text = ResString(R.string.common_section_title_model),
                    trailingText = ResString(R.string.common_button_see_more),
                    onClickMore = {
                        viewModel.onClickMoreSDModel()
                    }
                )
            }
            item {
                ModelsLayout(
                    models = requestModel.previewSDModels,
                    onClick = { option ->
                        viewModel.onSelectSDModel(
                            option = option as SDModelOption
                        )
                    },
                    loading = uiState.modelLoading
                )
            }

            // loRa
            item(
                contentType = "OptionTitle"
            ) {
                OptionTitleWithMore(
                    text = ResString(R.string.common_section_title_lora),
                    trailingText = ResString(R.string.common_button_see_more),
                    onClickMore = {
                        viewModel.onClickMoreLoRaModel()
                    }
                )
            }
            item {
                ModelsLayout(
                    models = requestModel.previewLoRas,
                    onClick = { option ->
                        viewModel.onSelectLoRaModel(
                            option = option as LoRaModelOption
                        )
                    },
                    loading = uiState.modelLoading
                )
            }
            requestModel.loRaModelsOptions
                .filter { option -> option.selected }
                .forEach { option ->
                    item {
                        OptionTitle(
                            text = ResString(
                                R.string.common_section_title_lora_strength,
                                option.title.getValue(),
                                option.strength.roundToOneDecimalPlace().toString()
                            )
                        )
                    }
                    item {
                        ZeroToOneSliderOptionLayout(
                            value = option.strength,
                            onValueChange = { strength ->
                                viewModel.onChangeLoRaModelStrength(
                                    option = option,
                                    strength = strength
                                )
                            }
                        )
                    }
                }

            // embeddings
            item(
                contentType = "OptionTitle"
            ) {
                OptionTitleWithMore(
                    text = ResString(R.string.common_section_title_embeddings),
                    trailingText = ResString(R.string.common_button_see_more),
                    onClickMore = {
                        viewModel.onClickMoreEmbeddingsModel()
                    }
                )
            }
            item {
                ModelsLayout(
                    models = requestModel.previewEmbeddings,
                    onClick = { option ->
                        viewModel.onSelectEmbeddingsModel(
                            option = option as EmbeddingsModelOption
                        )
                    },
                    loading = uiState.modelLoading
                )
            }

            // size type
            item(
                contentType = "OptionTitle"
            ) {
                OptionTitle(
                    text = ResString(R.string.common_section_title_size_type)
                )
            }
            item {
                RadioOptionLayout(
                    options = requestModel.sizeOption,
                    onClick = { option ->
                        viewModel.onSelectSizeType(
                            option = option
                        )
                    }
                )
            }

            // step count
            item(
                contentType = "OptionTitle"
            ) {
                OptionTitle(
                    text = ResString(
                        R.string.common_section_title_step_count,
                        requestModel.stepCount.toString()
                    )
                )
            }
            item {
                NaturalNumberSliderOptionLayout(
                    value = requestModel.stepCount,
                    onValueChange = { stepCount ->
                        viewModel.onChangeStepCount(stepCount)
                    },
                    valueRange = 1..50
                )
            }

            // upscale
            item(
                contentType = "OptionTitle"
            ) {
                OptionTitle(
                    text = ResString(R.string.common_section_title_upscale)
                )
            }
            item {
                RadioOptionLayout(
                    options = requestModel.upscaleOption,
                    onClick = { option ->
                        viewModel.onChangeUpscale(
                            upscale = option
                        )
                    }
                )
            }

            // seed
            item(
                contentType = "OptionTitle"
            ) {
                OptionTitleWithMore(
                    text = ResString(R.string.common_section_title_seed),
                    trailingText = if (requestModel.seed == null) {
                        ResString(R.string.common_random)
                    } else {
                        TextString(requestModel.seed.toString())
                    },
                    onClickMore = {
                        viewModel.onClickChangeSeed(requestModel.seed)
                    }
                )
            }

            // scheduler
            item(
                contentType = "OptionTitle"
            ) {
                OptionTitle(
                    text = ResString(R.string.common_section_title_scheduler)
                )
            }
            item {
                RadioOptionLayout(
                    options = requestModel.schedulerOption,
                    onClick = { option ->
                        viewModel.onChangeScheduler(
                            scheduler = (option as SchedulerOption)
                        )
                    }
                )
            }
        }
    }

    TxtToImgDialog(
        dialogState = dialogState,
        sdModels = requestModel.sdModelOption,
        loRaModels = requestModel.loRaModelsOptions,
        embeddingsModels = requestModel.embeddingsModelOption,
        onAddPositivePrompt = { prompt ->
            viewModel.onAddPositivePrompt(prompt = prompt)
        },
        onAddNegativePrompt = { prompt ->
            viewModel.onAddNegativePrompt(prompt = prompt)
        },
        onSeedChange = { seed ->
            viewModel.onChangeSeed(seed = seed)
        },
        onSelectSDModel = { option ->
            viewModel.onSelectSDModel(option = option)
        },
        onSelectLoRaModel = { option ->
            viewModel.onSelectLoRaModel(option = option)
        },
        onSelectEmbeddingsModelOption = { option ->
            viewModel.onSelectEmbeddingsModel(option = option)
        },
        onCancelDialog = {
            viewModel.onCancelDialog()
        }
    )

    if (uiState.loading) {
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
fun TxtToImgDialog(
    dialogState: TxtToImgDialogState,
    sdModels: List<SDModelOption>,
    loRaModels: List<LoRaModelOption>,
    embeddingsModels: List<EmbeddingsModelOption>,
    onAddPositivePrompt: (String) -> Unit,
    onAddNegativePrompt: (String) -> Unit,
    onSelectSDModel: (SDModelOption) -> Unit,
    onSelectLoRaModel: (LoRaModelOption) -> Unit,
    onSelectEmbeddingsModelOption: (EmbeddingsModelOption) -> Unit,
    onSeedChange: (Long?) -> Unit,
    onCancelDialog: () -> Unit
) {
    when (dialogState) {
        is PositivePromptAdditionDialog -> {
            InputDialog(
                onDismissRequest = onCancelDialog,
                onClickAddButton = onAddPositivePrompt
            )
        }

        is NegativePromptOptionAdditionDialog -> {
            InputDialog(
                onDismissRequest = onCancelDialog,
                onClickAddButton = onAddNegativePrompt
            )
        }

        is MoreSDModelBottomSheet -> {
            FullModelOptionBottomSheet(
                title = ResString(R.string.common_section_title_model),
                options = sdModels,
                onCancelDialog = {
                    onCancelDialog()
                },
                onSelectModelOption = {
                    onSelectSDModel(it as SDModelOption)
                }
            )
        }

        is MoreLoRaModelBottomSheet -> {
            FullModelOptionBottomSheet(
                title = ResString(R.string.common_section_title_model),
                options = loRaModels,
                onCancelDialog = {
                    onCancelDialog()
                },
                onSelectModelOption = {
                    onSelectLoRaModel(it as LoRaModelOption)
                }
            )
        }

        is MoreEmbeddingsBottomSheet -> {
            FullModelOptionBottomSheet(
                title = ResString(R.string.common_section_title_lora),
                options = embeddingsModels,
                onCancelDialog = {
                    onCancelDialog()
                },
                onSelectModelOption = {
                    onSelectEmbeddingsModelOption(it as EmbeddingsModelOption)
                }
            )
        }

        is SeedChangeDialog -> {
            InputDialog(
                onDismissRequest = onCancelDialog,
                onClickAddButton = {
                    onSeedChange(it.toLongOrNull())
                },
                placeholderText = ResString(R.string.common_section_title_embeddings),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
        }

        else -> {
            // do nothing
        }
    }
}

@Composable
fun SelectionScreen(
    requestModel: TxtToImgOptionState
) {
}
