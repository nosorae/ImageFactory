package com.yessorae.imagefactory.ui.screen.main.history

import com.yessorae.data.repository.TxtToImgHistoryRepository
import com.yessorae.imagefactory.mapper.TxtToImgHistoryMapper
import com.yessorae.imagefactory.util.base.BaseScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TxtToImgHistoryViewModel @Inject constructor(
    private val txtToImgHistoryRepository: TxtToImgHistoryRepository,
    private val txtToImgHistoryMapper: TxtToImgHistoryMapper
) : BaseScreenViewModel<TxtToImgHistoryScreenState>() {
    override val initialState: TxtToImgHistoryScreenState = TxtToImgHistoryScreenState.Loading

    init {
        getHistories()
    }
    private fun getHistories() = ioScope.launch {
        txtToImgHistoryRepository.getHistories().collectLatest { entities ->
            updateState {
                TxtToImgHistoryScreenState.View(
                    histories = txtToImgHistoryMapper.map(entities = entities)
                )
            }
        }
    }
}

