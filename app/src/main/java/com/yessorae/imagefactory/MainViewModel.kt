package com.yessorae.imagefactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yessorae.domain.usecase.InsertInitialPromptUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val insertInitialPromptUseCase: InsertInitialPromptUseCase
) : ViewModel() {
    fun processInitialData() = viewModelScope.launch(Dispatchers.IO) {
        insertInitialPromptUseCase()
    }
}
