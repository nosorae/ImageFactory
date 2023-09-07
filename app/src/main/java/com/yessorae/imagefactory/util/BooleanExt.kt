package com.yessorae.imagefactory.util

import com.yessorae.common.Constants

fun Boolean.yesOrNo(): String {
    return if (this) {
        Constants.ARG_YES
    } else {
        Constants.ARG_NO
    }
}
