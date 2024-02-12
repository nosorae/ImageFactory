package com.yessorae.presentation.di

import android.content.Context
import com.yessorae.presentation.util.StringResourceProvider
import com.yessorae.presentation.util.StringResourceProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {
    @Provides
    fun provideStringResourceProvider(
        @ApplicationContext context: Context,
    ): StringResourceProvider {
        return StringResourceProviderImpl(context = context)
    }
}