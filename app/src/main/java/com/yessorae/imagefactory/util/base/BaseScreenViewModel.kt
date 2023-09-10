package com.yessorae.imagefactory.util.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yessorae.common.Logger
import com.yessorae.imagefactory.R
import com.yessorae.imagefactory.util.ResString
import com.yessorae.imagefactory.util.StringModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BaseScreenViewModel<T> : ViewModel() {
    protected abstract val initialState: T

    protected val _state = MutableStateFlow(initialState)
    val state: StateFlow<T> = _state.asStateFlow()

    protected val stateValue: T get() = state.value

    protected val _toast = MutableSharedFlow<StringModel>()
    val toast: SharedFlow<StringModel> = _toast.asSharedFlow()

    protected val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent: SharedFlow<String> = _navigationEvent.asSharedFlow()

    protected val _backNavigationEvent = MutableSharedFlow<Unit>()
    val backNavigationEvent: SharedFlow<Unit> = _backNavigationEvent.asSharedFlow()

    protected val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    abstract val ceh: CoroutineExceptionHandler

    val ioScope = viewModelScope + Dispatchers.IO

    open fun updateState(newState: () -> T) = viewModelScope.launch {
        _state.update {
            val new = newState.invoke()
            Logger.presentation("message $new")
            new
        }
    }

    protected fun showLoading() {
        _loading.value = true
    }

    protected fun hideLoading() {
        _loading.value = false
    }

    protected fun showToast(message: StringModel) = viewModelScope.launch {
        _toast.emit(message)
    }
}
