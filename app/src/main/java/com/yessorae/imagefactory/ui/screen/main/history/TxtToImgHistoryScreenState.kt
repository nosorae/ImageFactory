package com.yessorae.imagefactory.ui.screen.main.history

import com.yessorae.domain.model.TxtToImgHistory

sealed class TxtToImgHistoryScreenState {
    object Loading : TxtToImgHistoryScreenState()
    data class View(
        val histories: List<TxtToImgHistory>
    ) : TxtToImgHistoryScreenState()
}
