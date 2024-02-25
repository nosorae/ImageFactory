package com.yessorae.domain.usecase

import com.yessorae.domain.repository.ImageUploadRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val imageUploadRepository: ImageUploadRepository
) {
    suspend operator fun invoke(image: ByteArray): String {
        return imageUploadRepository.uploadImage(bitmap = image)
    }
}