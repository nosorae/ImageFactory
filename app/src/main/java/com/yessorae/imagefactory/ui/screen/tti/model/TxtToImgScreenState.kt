package com.yessorae.imagefactory.ui.screen.tti.model

data class TxtToImgScreenState(
    val request: TxtToImgRequestModel = TxtToImgRequestModel(),
    val result: String? = null,
    val loading: Boolean = false
)
