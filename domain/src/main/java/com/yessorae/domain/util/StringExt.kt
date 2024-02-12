package com.yessorae.domain.util

fun String?.isMultiLanguage(): Boolean {
    if (isNullOrEmpty()) return false

    val regex = Regex("^[a-zA-Z0-9!@#\\$%^&*()_+\\-=\\[\\]{};':\",.<>\\/?\\s]*$")
    return !regex.matches(this)
}

suspend fun String.handleSdResponse(
    onSuccess: suspend () -> Unit,
    onProcessing: suspend () -> Unit,
    onError: suspend () -> Unit
) {
    when (this) {
        StableDiffusionConstants.RESPONSE_SUCCESS -> {
            onSuccess()
        }
        StableDiffusionConstants.RESPONSE_PROCESSING -> {
            onProcessing()
        }
        StableDiffusionConstants.RESPONSE_ERROR -> {
            onError()
        }
    }
}
