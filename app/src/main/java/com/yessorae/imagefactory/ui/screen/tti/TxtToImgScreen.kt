package com.yessorae.imagefactory.ui.screen.tti

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.model.type.SDSizeType
import com.yessorae.imagefactory.ui.components.item.OptionTitle
import com.yessorae.imagefactory.ui.components.item.OptionTitleWithMore
import com.yessorae.imagefactory.ui.components.layout.ModelsLayout
import com.yessorae.imagefactory.ui.components.layout.NaturalNumberSliderOptionLayout
import com.yessorae.imagefactory.ui.components.layout.OnOffOptionLayout
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

    SelectionScreen(
        requestModel = uiState.request
    )
}

@Composable
fun SelectionScreen(
    requestModel: TxtToImgRequestModel
) {
    LazyColumn {
        item {
            OptionTitleWithMore(
                text = ResString(R.string.common_section_title_positive_prompt),
                trailingText = ResString(R.string.common_section_button_custom_prompt),
                onClickMore = {
                    // todo
                }
            )
        }
        item {
        }

        item {
            OptionTitleWithMore(
                text = ResString(R.string.common_section_title_negative_prompt),
                trailingText = ResString(R.string.common_section_button_custom_prompt),
                onClickMore = {
                    // todo
                }
            )
        }
        item {
        }

        item {
            OnOffOptionLayout(
                text = ResString(R.string.common_section_title_prompt_enhancement),
                checked = requestModel.enhancePrompt,
                onCheckedChange = {
                    // todo
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
                onValueChange = {
                    // todo
                },
                valueRange = 1..20
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
                onClick = {
                    // todo
                }
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
                onClick = {
                    // todo
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
                onClick = {
                    // todo
                }
            )
        }
        requestModel.loRaModelsOptions
            .filter { it.selected }
            .forEach {
                item {
                    OptionTitle(
                        text = ResString(
                            R.string.common_section_title_lora_strength,
                            it.title.getValue(),
                            it.strength.roundToOneDecimalPlace().toString()
                        )
                    )
                }
                item {
                    ZeroToOneSliderOptionLayout(
                        value = it.strength,
                        onValueChange = {
                            // todo
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
                onClick = {
                    // todo
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
                onValueChange = {
                    // todo
                },
                valueRange = 1..50
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
                }
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
                onClick = {
                    // todo
                }
            )
        }
    }
}
