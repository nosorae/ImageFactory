package com.yessorae.imagefactory.util

import com.yessorae.data.util.StableDiffusionConstants

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
