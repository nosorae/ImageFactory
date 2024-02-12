package com.yessorae.domain.usecase

import com.yessorae.domain.model.UpscaleResult
import com.yessorae.domain.repository.ImageEditingRepository
import com.yessorae.domain.repository.ImageUploadRepository
import com.yessorae.domain.util.StableDiffusionConstants.DEFAULT_SAVED_IMAGE_NAME
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class UpscaleImgUseCase @Inject constructor(
    private val imageEditingRepository: ImageEditingRepository,
    private val imageUploadRepository: ImageUploadRepository
) {
    suspend operator fun invoke(bitmap: ByteArray): UpscaleResult {
        val url = imageUploadRepository.uploadImage(
            bitmap = bitmap,
            path = DEFAULT_SAVED_IMAGE_NAME,
            name = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            )
        return imageEditingRepository.upscaleImage(url = url)
    }
}