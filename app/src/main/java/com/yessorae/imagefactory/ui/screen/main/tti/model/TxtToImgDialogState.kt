package com.yessorae.imagefactory.ui.screen.main.tti.model

import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.model.EmbeddingsModelOption
import com.yessorae.imagefactory.model.LoRaModelOption
import com.yessorae.imagefactory.model.PromptOption
import com.yessorae.imagefactory.model.SDModelOption
import com.yessorae.imagefactory.ui.screen.result.model.TxtToImgResultModel
import com.yessorae.imagefactory.ui.screen.result.model.UpscaleResultModel
import com.yessorae.imagefactory.util.ResString
import com.yessorae.imagefactory.util.StringModel
import com.yessorae.imagefactory.util.TextString

sealed class TxtToImgDialogState

object None : TxtToImgDialogState()
object PositivePromptAdditionDialog : TxtToImgDialogState()
object NegativePromptOptionAdditionDialog : TxtToImgDialogState()

data class MorePositivePromptBottomSheet(
    val options: List<PromptOption>
) : TxtToImgDialogState()

data class MoreNegativePromptBottomSheet(
    val option: List<PromptOption>
) : TxtToImgDialogState()

data class MoreSDModelBottomSheet(
    val options: List<SDModelOption>
) : TxtToImgDialogState()

data class MoreLoRaModelBottomSheet(
    val options: List<LoRaModelOption>
) : TxtToImgDialogState()

data class MoreEmbeddingsBottomSheet(
    val options: List<EmbeddingsModelOption>
) : TxtToImgDialogState()

data class SeedChangeDialog(
    val currentSeed: Long?
) : TxtToImgDialogState() {
    val seedText: StringModel =
        currentSeed?.let { TextString(it.toString()) } ?: ResString(R.string.common_random)
}

data class TxtToImgResultDialog(
    val requestOption: TxtToImgOptionState,
    val width: Int,
    val height: Int,
    val result: TxtToImgResultModel? = null,
    val upscaleResultModel: UpscaleResultModel? = null,
    val fail: Boolean = false
) : TxtToImgDialogState() {
    val ratio: Float = width / height.toFloat()
}
