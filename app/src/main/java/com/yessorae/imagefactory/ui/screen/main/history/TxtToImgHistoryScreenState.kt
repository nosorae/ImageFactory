package com.yessorae.imagefactory.ui.screen.main.history

import com.yessorae.imagefactory.ui.model.TxtToImgHistory

sealed class TxtToImgHistoryScreenState {
    object Loading : TxtToImgHistoryScreenState()
    data class View(
        val histories: List<TxtToImgHistory>
    ) : TxtToImgHistoryScreenState()

    data class Edit(
        val histories: List<TxtToImgHistory>,
        val prevState: TxtToImgHistoryScreenState
    ) : TxtToImgHistoryScreenState()
}

