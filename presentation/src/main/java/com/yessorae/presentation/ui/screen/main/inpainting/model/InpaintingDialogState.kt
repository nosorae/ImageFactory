package com.yessorae.presentation.ui.screen.main.inpainting.model

sealed class InpaintingDialogState {
    object None: InpaintingDialogState()
    object ImageAddMethod: InpaintingDialogState()
}