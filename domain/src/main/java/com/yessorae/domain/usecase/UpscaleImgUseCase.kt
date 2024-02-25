package com.yessorae.domain.usecase

import com.yessorae.domain.model.UpscaleResult
import com.yessorae.domain.repository.ImageEditingRepository
import com.yessorae.domain.repository.ImageUploadRepository
import javax.inject.Inject

class UpscaleImgUseCase @Inject constructor(
    private val imageEditingRepository: ImageEditingRepository,
    private val imageUploadRepository: ImageUploadRepository
) {
    suspend operator fun invoke(bitmap: ByteArray): UpscaleResult {
        val url = imageUploadRepository.uploadImage(bitmap = bitmap)
        return imageEditingRepository.upscaleImage(url = url)
    }
}
