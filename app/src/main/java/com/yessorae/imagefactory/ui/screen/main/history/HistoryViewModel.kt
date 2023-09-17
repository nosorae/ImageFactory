package com.yessorae.imagefactory.ui.screen.main.history

import com.yessorae.data.repository.TxtToImgHistoryRepository
import com.yessorae.imagefactory.mapper.TxtToImgHistoryMapper
import com.yessorae.imagefactory.ui.model.TxtToImgHistory
import com.yessorae.imagefactory.util.base.BaseScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val txtToImgHistoryRepository: TxtToImgHistoryRepository,
    private val txtToImgHistoryMapper: TxtToImgHistoryMapper
) : BaseScreenViewModel<TxtToImgHistoryScreenState>() {
    override val initialState: TxtToImgHistoryScreenState = TxtToImgHistoryScreenState.Loading

    private val _dialogState = MutableStateFlow<HistoryDialogState>(HistoryDialogState.None)
    val dialogState = _dialogState.asStateFlow()


    init {
        getTxtToImgHistories()
    }

    fun onClickDeleteTxtToImgHistory(txtToImgHistory: TxtToImgHistory) {
        _dialogState.update {
            HistoryDialogState.Delete(
                txtToImgHistory = txtToImgHistory
            )
        }
    }

    fun onClickFetch(txtToImgHistory: TxtToImgHistory) = ioScope.launch {
        fetchTxtToImgHistory(txtToImgHistory = txtToImgHistory)
    }

    fun onClickImage(txtToImgHistory: TxtToImgHistory) = ioScope.launch {
        // todo
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
        txtToImgHistoryRepository.deleteHistory(id)
    }

    private suspend fun fetchTxtToImgHistory(txtToImgHistory: TxtToImgHistory) {
        updateState {
            TxtToImgHistoryScreenState.Loading
        }
        txtToImgHistoryRepository.fetchQueuedImage(
            id = txtToImgHistory.id,
            requestId = txtToImgHistory.result.id.toString()
        )
    }

    private fun getTxtToImgHistories() = ioScope.launch {
        txtToImgHistoryRepository.getHistories().collectLatest { entities ->
            updateState {
                TxtToImgHistoryScreenState.View(
                    histories = txtToImgHistoryMapper.map(entities = entities)
                )
            }
        }
    }
}

