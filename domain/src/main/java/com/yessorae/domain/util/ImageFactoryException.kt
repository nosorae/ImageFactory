package com.yessorae.domain.util

sealed class ImageFactoryException(override val message: String) : Exception(message) {
    class StableDiffusionApiException(message: String) : ImageFactoryException(message)
    class FirebaseStorageException(message: String) : ImageFactoryException(message)
}
