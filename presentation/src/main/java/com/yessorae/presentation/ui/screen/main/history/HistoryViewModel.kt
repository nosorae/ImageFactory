package com.yessorae.presentation.ui.screen.main.history

import androidx.lifecycle.viewModelScope
import com.yessorae.common.Logger
import com.yessorae.domain.model.TxtToImgHistory
import com.yessorae.domain.usecase.DeleteTxtToImgHistoryUseCase
import com.yessorae.domain.usecase.GetTxtToImgHistoriesUseCase
import com.yessorae.presentation.navigation.destination.TxtToImgResultDestination
import com.yessorae.presentation.ui.screen.main.history.model.HistoryDialogState
import com.yessorae.presentation.ui.screen.main.history.model.TxtToImgHistoryScreenState
import com.yessorae.presentation.util.base.BaseScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getTxtToImgHistoriesUseCase: GetTxtToImgHistoriesUseCase,
    private val deleteTxtToImgHistoryUseCase: DeleteTxtToImgHistoryUseCase
) : BaseScreenViewModel() {
    private val _state = MutableStateFlow<TxtToImgHistoryScreenState>(TxtToImgHistoryScreenState.Loading)
    val state = _state.asStateFlow().onSubscription {
        initTxtToImgHistories()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = TxtToImgHistoryScreenState.Loading
    )

    private val _dialogState = MutableStateFlow<HistoryDialogState>(HistoryDialogState.None)
    val dialogState = _dialogState.asStateFlow()


    fun onClickDeleteTxtToImgHistory(txtToImgHistory: TxtToImgHistory) {
        _dialogState.update {
            HistoryDialogState.Delete(
                txtToImgHistory = txtToImgHistory
            )
        }
    }

    fun onClickFetch(txtToImgHistory: TxtToImgHistory) = ioScope.launch {
        _navigationEvent.emit(TxtToImgResultDestination.getRouteWithArgs(requestId = txtToImgHistory.id))
    }

    fun onClickImage(txtToImgHistory: TxtToImgHistory) = ioScope.launch {
        _navigationEvent.emit(TxtToImgResultDestination.getRouteWithArgs(requestId = txtToImgHistory.id))
    }

    fun onClickConfirmTxtToImgHistory(txtToImgHistory: TxtToImgHistory) = ioScope.launch {
        deleteTxtToImgHistory(id = txtToImgHistory.id)
    }

    fun onCancelDialog() {
        _dialogState.update {
            HistoryDialogState.None
        }
    }

    private suspend fun deleteTxtToImgHistory(id: Int) {
        deleteTxtToImgHistoryUseCase(historyId = id)
    }

    private fun initTxtToImgHistories() = ioScope.launch {
        getTxtToImgHistoriesUseCase().collectLatest { history ->
            Logger.presentation("history $history")
            _state.update {
                TxtToImgHistoryScreenState.View(
                    histories = history
                )
            }
        }
    }
}
