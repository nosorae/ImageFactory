package com.yessorae.imagefactory.ui.screen.main.tti.model

data class TxtToImgScreenState(
    val request: TxtToImgOptionRequest = TxtToImgOptionRequest(),
    val dialogState: TxtToImgDialog = TxtToImgDialog.None,
    val loading: Boolean = false,
    val modelLoading: Boolean = true
)
