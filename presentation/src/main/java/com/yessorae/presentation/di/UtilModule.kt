package com.yessorae.presentation.di

import android.content.Context
import com.yessorae.presentation.util.helper.ImageHelper
import com.yessorae.presentation.util.helper.ImageHelperImpl
import com.yessorae.presentation.util.helper.ImageSegmentationHelper
import com.yessorae.presentation.util.helper.ImageSegmentationHelperImpl
import com.yessorae.presentation.util.helper.StringResourceHelper
import com.yessorae.presentation.util.helper.StringResourceHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {
    @Provides
    fun provideStringResourceHelper(
        @ApplicationContext context: Context
    ): StringResourceHelper {
        return StringResourceHelperImpl(context = context)
    }

    @Provides
    fun provideImageHelper(
        @ApplicationContext context: Context
    ): ImageHelper {
        return ImageHelperImpl(context = context)
    }

    @Provides
    fun provideImageSegmentationHelper(
        @ApplicationContext context: Context
    ): ImageSegmentationHelper {
        return ImageSegmentationHelperImpl(context = context)
    }
}