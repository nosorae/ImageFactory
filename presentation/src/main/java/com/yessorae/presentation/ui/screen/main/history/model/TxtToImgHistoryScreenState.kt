package com.yessorae.presentation.ui.screen.main.history.model

import com.yessorae.domain.model.TxtToImgHistory

sealed class TxtToImgHistoryScreenState {
    object Loading : TxtToImgHistoryScreenState()
    data class View(
        val histories: List<TxtToImgHistory>
    ) : TxtToImgHistoryScreenState()
}
