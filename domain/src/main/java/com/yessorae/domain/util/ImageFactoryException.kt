package com.yessorae.domain.util

sealed class ImageFactoryException(override val message: String = "") : Exception(message)
// https://stablediffusionapi.com/~ 요청에 실패했을 때
class StableDiffusionApiException(message: String) : ImageFactoryException(message = message)

// 이미지 관련 요청 했는데 처리 중일 때 (2~3초 후에 fetch api 를 요청 하라는 가이드)
object ProcessingException: ImageFactoryException()

// 이미지 관련 요청 했는데 중간에 에러가 발생한 경우
class ProcessingErrorException(message: String): ImageFactoryException(message = message)

class FirebaseStorageException(message: String) : ImageFactoryException(message = message)

