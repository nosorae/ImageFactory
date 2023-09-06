package com.yessorae.imagefactory.ui.screen.main.tti.model

import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.model.EmbeddingsModelOption
import com.yessorae.imagefactory.model.LoRaModelOption
import com.yessorae.imagefactory.model.PromptOption
import com.yessorae.imagefactory.model.SDModelOption
import com.yessorae.imagefactory.ui.util.ResString
import com.yessorae.imagefactory.ui.util.StringModel
import com.yessorae.imagefactory.ui.util.TextString

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
    val request: TxtToImgRequestModel,
    val result: TxtToImgResultModel? = null,
    val ratio: Float,
) : TxtToImgDialogState()