package com.yessorae.imagefactory.ui.screen.main.history

import com.yessorae.imagefactory.ui.model.TxtToImgHistory

sealed class HistoryDialogState {
    object None : HistoryDialogState()
    data class Delete(
        val txtToImgHistory: TxtToImgHistory
    ) : HistoryDialogState()
}
