package com.yessorae.imagefactory.ui.screen.main.tti.model

import com.yessorae.domain.util.yesOrNo
import com.yessorae.data.remote.stablediffusion.model.request.TxtToImgRequestDto
import com.yessorae.imagefactory.R
import com.yessorae.domain.model.option.EmbeddingsModelOption
import com.yessorae.domain.model.option.LoRaModelOption
import com.yessorae.domain.model.option.PromptOption
import com.yessorae.domain.model.option.SDModelOption
import com.yessorae.domain.model.option.SchedulerOption
import com.yessorae.domain.model.option.initialValues
import com.yessorae.domain.model.option.isMultiLingual
import com.yessorae.domain.model.option.toLoRaModelPrompt
import com.yessorae.domain.model.option.toLoRaStrengthPrompt
import com.yessorae.domain.model.option.toPrompt
import com.yessorae.domain.model.type.SDSizeType
import com.yessorae.imagefactory.model.type.UpscaleType
import com.yessorae.imagefactory.model.type.toSDSizeType
import com.yessorae.imagefactory.model.type.toUpscaleType
import com.yessorae.domain.model.option.Option
import com.yessorae.domain.model.option.getSelectedOption
import com.yessorae.imagefactory.util.ResString
import com.yessorae.imagefactory.util.StringModel

data class TxtToImgRequestOption(
    val positivePromptOptions: List<PromptOption> = listOf(),
    val negativePromptOptions: List<PromptOption> = listOf(),
    val sdModelOption: List<SDModelOption> = listOf(),
    val enhancePrompt: Boolean = true,
    val loRaModelsOptions: List<LoRaModelOption> = listOf(),
    val embeddingsModelOption: List<EmbeddingsModelOption> = listOf(),
    val stepCount: Int = DEFAULT_STEP_COUNT,
    val safetyChecker: Boolean = false,
    val sizeOption: List<Option> = SDSizeType.defaultOptions,
    val seed: Long? = null,
    // Scale for classifier-free guidance (minimum: 1; maximum: 20)
    val guidanceScale: Int = DEFAULT_GUIDANCE_SCALE, // todo 적정값 찾기
    val upscaleOption: List<Option> = UpscaleType.defaultOptions,
    val samples: Int = DEFAULT_SAMPLES,
    val schedulerOption: List<SchedulerOption> = SchedulerOption.initialValues()
) {
    private val multiLingual: Boolean by lazy {
        positivePromptOptions.isMultiLingual() || negativePromptOptions.isMultiLingual()
    }

    val previewSDModels by lazy {
        sdModelOption.filterIndexed { index, sdModelOption -> sdModelOption.selected || index < PREVIEW_COUNT }
    }

    val previewLoRas by lazy {
        loRaModelsOptions.filterIndexed { index, sdModelOption -> sdModelOption.selected || index < PREVIEW_COUNT }
    }

    val previewEmbeddings by lazy {
        embeddingsModelOption.filterIndexed { index, sdModelOption -> sdModelOption.selected || index < PREVIEW_COUNT }
    }

    fun asTxtToImgRequest(
        toastEvent: (StringModel) -> Unit
    ): TxtToImgRequestDto? {
        if (positivePromptOptions.count { it.selected } == 0) {
            toastEvent(ResString(R.string.common_warning_input_prompt))
            return null
        }
        val modelId = sdModelOption.getSelectedOption()?.id ?: run {
            toastEvent(ResString(R.string.common_warning_select_model))
            return null
        }
        val size = sizeOption.getSelectedOption()?.toSDSizeType() ?: run {
            toastEvent(ResString(R.string.common_warning_select_size))
            return null
        }
        val scheduler = schedulerOption.getSelectedOption()?.id ?: run {
            toastEvent(ResString(R.string.common_warning_select_scheduler))
            return null
        }

        return TxtToImgRequestDto(
            modelId = modelId,
            prompt = positivePromptOptions.toPrompt(),
            negativePrompt = negativePromptOptions.toPrompt(),
            width = size.width,
            height = size.height,
            samples = samples,
            numInferenceSteps = stepCount,
            safetyChecker = safetyChecker.yesOrNo(),
            enhancePrompt = enhancePrompt.yesOrNo(),
            seed = seed?.let { "$it" },
            guidanceScale = guidanceScale.toDouble(),
            loraStrength = loRaModelsOptions.toLoRaStrengthPrompt(),
            loraModel = loRaModelsOptions.toLoRaModelPrompt(),
            multiLingual = multiLingual.yesOrNo(),
            upscale = upscaleOption.getSelectedOption()?.toUpscaleType()?.value
                ?: UpscaleType.None.value,
            embeddingsModel = embeddingsModelOption.getSelectedOption()?.id,
            scheduler = scheduler
        )
    }

    companion object {
        const val PREVIEW_COUNT = 10
        const val DEFAULT_GUIDANCE_SCALE = 10
        const val DEFAULT_SAMPLES = 1
        const val DEFAULT_STEP_COUNT = 1
    }
}
