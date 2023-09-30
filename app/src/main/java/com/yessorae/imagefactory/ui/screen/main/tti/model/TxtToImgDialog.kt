package com.yessorae.imagefactory.ui.screen.main.tti.model

import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.model.EmbeddingsModelOption
import com.yessorae.imagefactory.model.LoRaModelOption
import com.yessorae.imagefactory.model.PromptOption
import com.yessorae.imagefactory.model.SDModelOption
import com.yessorae.imagefactory.ui.model.UpscaleResultModel
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
        val result: com.yessorae.imagefactory.ui.model.TxtToImgResult? = null,
        val upscaleResultModel: UpscaleResultModel? = null,
        val fail: Boolean = false
    ) : TxtToImgDialog() {
        val ratio: Float = width / height.toFloat()
    }

}

