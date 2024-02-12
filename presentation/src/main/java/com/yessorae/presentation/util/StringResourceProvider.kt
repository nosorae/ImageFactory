package com.yessorae.presentation.util

import android.content.Context
import androidx.annotation.StringRes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

interface StringResourceProvider {
    fun getString(@StringRes resourceId: Int, vararg parameters: String): String
}

@Singleton
class StringResourceProviderImpl @Inject constructor(
    private val context: Context
) : StringResourceProvider {
    override fun getString(resourceId: Int, vararg parameters: String): String {
        return context.getString(resourceId, parameters)
    }
}

