package com.yessorae.presentation.ui.screen.main.inpainting.model

import com.yessorae.presentation.ui.screen.main.tti.model.PromptOption
import com.yessorae.presentation.ui.screen.main.tti.model.TxtToImgDialog

sealed class InpaintingDialogState {
    object None: InpaintingDialogState()
    object ImageAddMethod: InpaintingDialogState()
    object PositivePromptAddition : InpaintingDialogState()
    object NegativePromptOptionAddition : InpaintingDialogState()
    object MoreSDModelBottomSheet: InpaintingDialogState()

    data class PromptDeletionConfirmation(
        val title: String,
        val promptOption: PromptOption
    ): InpaintingDialogState()
}