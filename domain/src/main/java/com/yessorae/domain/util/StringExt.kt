package com.yessorae.domain.util

fun String.isMultiLanguage(): Boolean {
    val regex = Regex("^[a-zA-Z0-9!@#\\$%^&*()_+\\-=\\[\\]{};':\",.<>\\/?\\s]*$")
    return !regex.matches(this)
}

suspend fun String.handleSdResponse(
    onSuccess: suspend () -> Unit,
    onProcessing: suspend () -> Unit,
    onError: suspend () -> Unit
) {
    when (this) {
        StableDiffusionApiConstants.RESPONSE_SUCCESS -> {
            onSuccess()
        }
        StableDiffusionApiConstants.RESPONSE_PROCESSING -> {
            onProcessing()
        }
        StableDiffusionApiConstants.RESPONSE_ERROR -> {
            onError()
        }
    }
}
