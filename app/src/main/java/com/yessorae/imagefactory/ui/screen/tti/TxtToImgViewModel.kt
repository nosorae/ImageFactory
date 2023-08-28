package com.yessorae.imagefactory.ui.screen.tti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yessorae.common.Constants
import com.yessorae.common.Logger
import com.yessorae.data.model.request.TxtToImgRequest
import com.yessorae.data.model.response.TxtToImgResponse
import com.yessorae.data.repository.TxtToImgRepository
import com.yessorae.imagefactory.model.EmbeddingsModelOption
import com.yessorae.imagefactory.model.LoRaModelOption
import com.yessorae.imagefactory.model.PromptOption
import com.yessorae.imagefactory.model.SchedulerOption
import com.yessorae.imagefactory.model.isMultiLingual
import com.yessorae.imagefactory.model.type.SDSizeType
import com.yessorae.imagefactory.model.type.UpscaleType
import com.yessorae.imagefactory.model.initialValues
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

    private val _uiState = MutableStateFlow<TxtToImageUiState>(TxtToImageUiState.Input)
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
            modelId = "midjourney",
            prompt = "sunset, beach, ocean",
            negativePrompt = "human, person, people",
            width = 512,
            height = 512,
            samples = 1,
            numInferenceSteps = 25,
            safetyChecker = Constants.ARG_YES,
            safetyCheckerType = "blur",
            enhancePrompt = Constants.ARG_YES,
            seed = null,
            guidanceScale = 7.5,
            tomesd = Constants.ARG_YES,
            useKarrasSigmas = Constants.ARG_YES,
            algorithmType = "sde-dpmsolver++",
            vae = null,
            loraStrength = null,
            loraModel = null,
            multiLingual = Constants.ARG_NO,
            panorama = Constants.ARG_NO,
            selfAttention = Constants.ARG_NO,
            upscale = "1",
            clipSkip = 4,
            base64 = Constants.ARG_NO,
            embeddingsModel = null,
            scheduler = "UniPCMultistepScheduler",
            webhook = null,
            trackId = null,
            temp = Constants.ARG_YES
        )

        _uiState.value = TxtToImageUiState.Loading
        _uiState.value =
            TxtToImageUiState.Success(response = txtToImgRepository.generateImage(dreamBoothRequest))
    }
}

sealed class TxtToImageUiState {

    object Input : TxtToImageUiState()
    object Loading : TxtToImageUiState()
    data class Success(
        val response: TxtToImgResponse
    ) : TxtToImageUiState()

    object Error : TxtToImageUiState()
}


data class TxtToImgScreenState(
    val request: TxtToImgRequest
)

data class TxtToImgRequest(
    val promptOptions: List<PromptOption> = listOf(),
    val negativePromptOptions: List<PromptOption> = listOf(),
    val size: SDSizeType = SDSizeType.Square,
    val samples: Int = 1,
    val numInferenceSteps: Int = 1,
    val safetyChecker: Boolean = false,
    val enhancePrompt: Boolean = true,
    val seed: Long? = null,
    // Scale for classifier-free guidance (minimum: 1; maximum: 20)
    val guidanceScale: Int = 10, // todo 적정값 찾기
    val loRaModelsOptions: List<LoRaModelOption>,
    val upscale: UpscaleType = UpscaleType.None,
    val embeddingsModel: List<EmbeddingsModelOption> = listOf(),
    val scheduler: List<SchedulerOption> = SchedulerOption.initialValues(),
) {
    // options: blur/sensitive_content_text/pixelate/black
    val safetyCheckerType: String = "blur"

    // Used in DPMSolverMultistepScheduler scheduler, default: none, options: sde-dpmsolver++
    val algorithmType = "sde-dpmsolver++"

    // Enable tomesd to generate images: gives really fast results, default: yes, options: yes/no
    val tomesd: String = Constants.ARG_YES

    // Use keras sigmas to generate images. gives nice results, default: yes, options: yes/no
    val useKarrasSigmas: String = Constants.ARG_YES

    // use custom vae in generating images default: null
    val vae: String? = null

    // Set this parameter to "yes" to generate a panorama image.
    val panorama: String = Constants.ARG_NO

    // If you want a high quality image, set this parameter to "yes". In this case the image generation will take more time.
    val selfAttention: String = Constants.ARG_YES

    // Clip Skip (minimum: 1; maximum: 8)
    val clipSkip: Int = 2

    // Get response as base64 string, default: "no", options: yes/no
    val base64: String = Constants.ARG_NO

    val webhook: String? = null
    val trackId: String? = null
    val temp = Constants.ARG_YES


    val multiLingual: Boolean by lazy {
        promptOptions.isMultiLingual() || negativePromptOptions.isMultiLingual()
    }

    fun asTxtToImgRequest() {
        // todo impl
    }

}