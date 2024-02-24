package com.yessorae.presentation.ui.screen.main.history.model

import com.yessorae.domain.model.tti.TxtToImgHistory

sealed class HistoryDialogState {
    object None : HistoryDialogState()
    data class Delete(
        val txtToImgHistory: TxtToImgHistory
    ) : HistoryDialogState()
}
