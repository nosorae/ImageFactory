package com.yessorae.imagefactory.ui.util

fun String.isMultiLanguage(): Boolean {
    val regex = Regex("^[a-zA-Z0-9!@#\\$%^&*()_+\\-=\\[\\]{};':\",.<>\\/?\\s]*$")
    return !regex.matches(this)
}
