package com.yessorae.presentation.ui.screen.main.inpainting

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.screen.main.inpainting.model.InpaintingDialogState
import com.yessorae.presentation.ui.screen.main.inpainting.model.InPaintingUiState
import com.yessorae.presentation.ui.screen.main.inpainting.model.SegmentationLabel
import com.yessorae.presentation.util.helper.ImageHelper
import com.yessorae.presentation.util.base.BaseScreenViewModel
import com.yessorae.presentation.util.helper.ImageSegmentationHelper
import com.yessorae.presentation.util.helper.StringResourceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InpaintingViewModel @Inject constructor(
    private val imageHelper: ImageHelper,
    private val imageSegmentationHelper: ImageSegmentationHelper,
    private val stringResourceHelper: StringResourceHelper
) : BaseScreenViewModel() {

    lateinit var initialBitmap: Bitmap

    private val _uiState = MutableStateFlow<InPaintingUiState>(InPaintingUiState.Initial)
    val uiState: StateFlow<InPaintingUiState> = _uiState.asStateFlow()

    private val _dialogState = MutableStateFlow<InpaintingDialogState>(InpaintingDialogState.None)
    val dialogState = _dialogState.asStateFlow()

    private val _takePicture = MutableSharedFlow<Unit>()
    val takePicture = _takePicture.asSharedFlow()

    private val _pickImage = MutableSharedFlow<Unit>()
    val pickImage = _pickImage.asSharedFlow()

    fun clickAddImage() = viewModelScope.launch {
        _dialogState.emit(InpaintingDialogState.ImageAddMethod)
    }

    fun clickTakePicture() = viewModelScope.launch {
        _takePicture.emit(Unit)
    }

    fun clickPickImage() = viewModelScope.launch {
        _pickImage.emit(Unit)
    }

    fun completeTakePicture(bitmap: Bitmap) {
        _uiState.update {
            InPaintingUiState.Image(
                initialBitmap = bitmap
            )
        }
        initialBitmap = bitmap
    }

    fun completePickImage(uri: Uri) {
        val bitmap = imageHelper.uriToBitmapWithSizeLimit512(uri = uri)
        _uiState.update {
            InPaintingUiState.Image(
                initialBitmap = bitmap
            )
        }
        initialBitmap = bitmap
    }

    fun segmentImage() = viewModelScope.launch {

        val result = imageSegmentationHelper.segmentImageFile(
            (uiState.value as InPaintingUiState.Image).initialBitmap
        )

        val mpImage = result?.categoryMask()?.get() ?: run {
            _toast.emit(stringResourceHelper.getString(R.string.common_response_unknown_error))
            return@launch
        }

        _uiState.update {
            InPaintingUiState.MaskedImage(
                initialBitmap = initialBitmap,
                maskedBitmap = imageSegmentationHelper.mapMPImageToBitmap(
                    mpImage = mpImage,
                    label = SegmentationLabel.ALL
                ),
                mpImage = mpImage
            )
        }
    }

    fun onClickSegmentationLabel(label: SegmentationLabel) = viewModelScope.launch {
        (uiState.value as? InPaintingUiState.MaskedImage)?.let { state ->
            val newImage = imageSegmentationHelper.mapMPImageToBitmap(mpImage = state.mpImage, label = label)
            _uiState.update {
                state.copy(
                    maskedBitmap = newImage
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        imageSegmentationHelper.clearImageSegmenter()
    }

    fun cancelDialog() = viewModelScope.launch {
        _dialogState.emit(InpaintingDialogState.None)
    }
}


// TODO:: SR-N Refactoring
fun Int.toAlphaColor(): Int {
    return Color.argb(
        128,
        Color.red(this),
        Color.green(this),
        Color.blue(this)
    )
}

val labelColors = listOf(
    -16777216,
    -8388608,
    -16744448,
    -8355840,
    -16777088,
    -8388480,
    -16744320,
    -8355712,
    -12582912,
    -4194304,
    -12550144,
    -4161536,
    -12582784,
    -4194176,
    -12550016,
    -4161408,
    -16760832,
    -8372224,
    -16728064,
    -8339456,
    -16760704
)