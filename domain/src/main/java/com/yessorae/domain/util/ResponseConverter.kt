package com.yessorae.domain.util

fun Boolean.yesOrNo(): String {
    return if (this) {
        Constants.ARG_YES
    } else {
        Constants.ARG_NO
    }
}

fun String.trueOrFalse(): Boolean {
    return this == Constants.ARG_YES
}
