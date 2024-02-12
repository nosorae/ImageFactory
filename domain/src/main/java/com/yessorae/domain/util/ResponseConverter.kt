package com.yessorae.domain.util

fun Boolean.yesOrNo(): String {
    return if (this) {
        StableDiffusionConstants.ARG_YES
    } else {
        StableDiffusionConstants.ARG_NO
    }
}

fun String.trueOrFalse(): Boolean {
    return this == StableDiffusionConstants.ARG_YES
}
