package com.yessorae.presentation.ui.screen.main.tti.model

sealed class TxtToImgDialog {
    object None : TxtToImgDialog()
    object PositivePromptAddition : TxtToImgDialog()
    object NegativePromptOptionAddition : TxtToImgDialog()
    object MoreSDModelBottomSheet: TxtToImgDialog()
    object MoreLoRaModelBottomSheet: TxtToImgDialog()
    object MoreEmbeddingsBottomSheet: TxtToImgDialog()

    data class PromptDeletionConfirmation(
        val title: String,
        val promptOption: PromptOption
    ): TxtToImgDialog()
}
