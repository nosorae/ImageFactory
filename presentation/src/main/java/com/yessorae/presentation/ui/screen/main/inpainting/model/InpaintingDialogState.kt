package com.yessorae.presentation.ui.screen.main.inpainting.model

import androidx.compose.runtime.Stable
import com.yessorae.presentation.ui.screen.main.tti.model.PromptOption
import com.yessorae.presentation.ui.screen.main.tti.model.TxtToImgDialog
@Stable
sealed class InpaintingDialogState {
    @Stable
    object None: InpaintingDialogState()
    @Stable
    object ImageAddMethod: InpaintingDialogState()
    @Stable
    object PositivePromptAddition : InpaintingDialogState()
    @Stable
    object NegativePromptOptionAddition : InpaintingDialogState()
    @Stable
    object MoreSDModelBottomSheet: InpaintingDialogState()
    @Stable
    data class PromptDeletionConfirmation(
        val title: String,
        val promptOption: PromptOption
    ): InpaintingDialogState()
}