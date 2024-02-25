package com.yessorae.presentation.ui.screen.main.tti.model

import androidx.compose.runtime.Stable

@Stable
sealed class TxtToImgDialog {
    @Stable
    object None : TxtToImgDialog()
    @Stable
    object PositivePromptAddition : TxtToImgDialog()
    @Stable
    object NegativePromptOptionAddition : TxtToImgDialog()
    @Stable
    object MoreSDModelBottomSheet: TxtToImgDialog()
    @Stable
    object MoreLoRaModelBottomSheet: TxtToImgDialog()
    @Stable
    object MoreEmbeddingsBottomSheet: TxtToImgDialog()

    @Stable
    data class PromptDeletionConfirmation(
        val title: String,
        val promptOption: PromptOption
    ): TxtToImgDialog()
}
