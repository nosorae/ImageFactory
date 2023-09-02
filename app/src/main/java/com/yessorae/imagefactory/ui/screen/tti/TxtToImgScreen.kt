package com.yessorae.imagefactory.ui.screen.tti

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.model.EmbeddingsModelOption
import com.yessorae.imagefactory.model.LoRaModelOption
import com.yessorae.imagefactory.model.PromptOption
import com.yessorae.imagefactory.model.SDModelOption
import com.yessorae.imagefactory.model.SchedulerOption
import com.yessorae.imagefactory.model.type.SDSizeType
import com.yessorae.imagefactory.model.type.UpscaleType
import com.yessorae.imagefactory.ui.components.item.OptionTitle
import com.yessorae.imagefactory.ui.components.item.OptionTitleWithMore
import com.yessorae.imagefactory.ui.components.layout.ModelsLayout
import com.yessorae.imagefactory.ui.components.layout.NaturalNumberSliderOptionLayout
import com.yessorae.imagefactory.ui.components.layout.OnOffOptionLayout
import com.yessorae.imagefactory.ui.components.layout.PromptOptionLayout
import com.yessorae.imagefactory.ui.components.layout.RadioOptionLayout
import com.yessorae.imagefactory.ui.components.layout.ZeroToOneSliderOptionLayout
import com.yessorae.imagefactory.ui.components.layout.roundToOneDecimalPlace
import com.yessorae.imagefactory.ui.screen.tti.model.TxtToImgRequestModel
import com.yessorae.imagefactory.ui.util.ResString
import com.yessorae.imagefactory.ui.util.TextString

@Composable
fun TxtToImgScreen(
    viewModel: TxtToImgViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val requestModel = uiState.request

    LazyColumn {
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
                onValueChange = { strength ->
                    viewModel.onChangePromptStrength(
                        strength = strength
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
                trailingText = ResString(R.string.common_button_see_more)
            )
        }
        item {
            ModelsLayout(
                models = requestModel.sdModelOption,
                onClick = { option ->
                    viewModel.onSelectSDModel(
                        option = option as SDModelOption
                    )
                }
            )
        }

        // loRa
        item(
            contentType = "OptionTitle"
        ) {
            OptionTitleWithMore(
                text = ResString(R.string.common_section_title_lora),
                trailingText = ResString(R.string.common_button_see_more)
            )
        }
        item {
            ModelsLayout(
                models = requestModel.loRaModelsOptions,
                onClick = { option ->
                    viewModel.onSelectLoRaModel(
                        option = option as LoRaModelOption
                    )
                }
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
                trailingText = ResString(R.string.common_button_see_more)
            )
        }
        item {
            ModelsLayout(
                models = requestModel.embeddingsModelOption,
                onClick = { option ->
                    viewModel.onSelectEmbeddingsModel(
                        option = option as EmbeddingsModelOption
                    )
                }
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
                options = SDSizeType.defaultOptions,
                onClick = { option ->
                    viewModel.onSelectSizeType(
                        sizeType = option
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
                options = UpscaleType.defaultOptions,
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
                    viewModel.onClickSeed(requestModel.seed)
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
                options = requestModel.scheduler,
                onClick = { option ->
                    viewModel.onChangeScheduler(
                        scheduler = (option as SchedulerOption)
                    )
                }
            )
        }
    }
}

@Composable
fun SelectionScreen(
    requestModel: TxtToImgRequestModel
) {

}
