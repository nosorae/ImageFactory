package com.yessorae.imagefactory.ui.screen.main.tti.model

import com.yessorae.imagefactory.R
import com.yessorae.domain.model.option.EmbeddingsModelOption
import com.yessorae.domain.model.option.LoRaModelOption
import com.yessorae.domain.model.option.PromptOption
import com.yessorae.domain.model.option.SDModelOption
import com.yessorae.domain.model.UpscaleResult
import com.yessorae.imagefactory.util.ResString
import com.yessorae.imagefactory.util.StringModel
import com.yessorae.imagefactory.util.TextString

sealed class TxtToImgDialog {
    object None : TxtToImgDialog()
    object PositivePromptAddition : TxtToImgDialog()
    object NegativePromptOptionAddition : TxtToImgDialog()

    data class MorePositivePromptBottomSheet(
        val options: List<PromptOption>
    ) : TxtToImgDialog()

    data class MoreNegativePromptBottomSheet(
        val option: List<PromptOption>
    ) : TxtToImgDialog()

    data class MoreSDModelBottomSheet(
        val options: List<SDModelOption>
    ) : TxtToImgDialog()

    data class MoreLoRaModelBottomSheet(
        val options: List<LoRaModelOption>
    ) : TxtToImgDialog()

    data class MoreEmbeddingsBottomSheet(
        val options: List<EmbeddingsModelOption>
    ) : TxtToImgDialog()

    data class SeedChange(
        val currentSeed: Long?
    ) : TxtToImgDialog() {
        val seedText: StringModel =
            currentSeed?.let { TextString(it.toString()) } ?: ResString(R.string.common_random)
    }

    data class TxtToImgResult(
        val requestOption: TxtToImgRequestOption,
        val width: Int,
        val height: Int,
        val result: com.yessorae.domain.model.TxtToImgResult? = null,
        val upscaleResult: UpscaleResult? = null,
        val fail: Boolean = false
    ) : TxtToImgDialog() {
        val ratio: Float = width / height.toFloat()
    }
}
