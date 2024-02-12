package com.yessorae.presentation.util.helper

import androidx.annotation.StringRes

interface StringResourceHelper {
    fun getString(@StringRes resourceId: Int, vararg parameters: String): String
}

