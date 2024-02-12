package com.yessorae.presentation.util.helper

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringResourceHelperImpl @Inject constructor(
    private val context: Context
) : StringResourceHelper {
    override fun getString(resourceId: Int, vararg parameters: String): String {
        return context.getString(resourceId, parameters)
    }
}