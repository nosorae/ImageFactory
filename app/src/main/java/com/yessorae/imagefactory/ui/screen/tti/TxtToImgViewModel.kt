package com.yessorae.imagefactory.ui.screen.tti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yessorae.common.Constants
import com.yessorae.common.Logger
import com.yessorae.data.model.request.TxtToImgRequest
import com.yessorae.data.model.response.TxtToImgDto
import com.yessorae.data.repository.TxtToImgRepository
import com.yessorae.imagefactory.ui.screen.tti.model.TxtToImgScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class TxtToImgViewModel @Inject constructor(
    private val txtToImgRepository: TxtToImgRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TxtToImgScreenState())
    val uiState = _uiState.asStateFlow()

    val ceh = CoroutineExceptionHandler { _, throwable ->
        Logger.presentation(
            message = throwable.toString(),
            error = true
        )
    }

    val scope = viewModelScope + ceh + Dispatchers.IO

    fun generateImage() = scope.launch {
        val dreamBoothRequest = TxtToImgRequest(
            prompt = "sunset, beach, ocean",
            negativePrompt = "human, person, people",
            enhancePrompt = Constants.ARG_YES,
            guidanceScale = 7.5,
            samples = 1,
            width = 512,
            height = 512,
            modelId = "midjourney",
            loraModel = null,
            loraStrength = null,
            embeddingsModel = null,
            numInferenceSteps = 25,
            safetyChecker = Constants.ARG_YES,
            seed = null,
            multiLingual = Constants.ARG_NO,
            upscale = "1",
            clipSkip = 4,
            scheduler = "UniPCMultistepScheduler"
        )
    }
}
