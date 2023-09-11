package com.yessorae.data.di

import com.yessorae.data.local.database.ImageFactoryDatabase
import com.yessorae.data.local.database.dao.PromptDao
import com.yessorae.data.local.database.dao.PublicModelDao
import com.yessorae.data.local.database.dao.TxtToImgHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun providePromptDao(
        database: ImageFactoryDatabase
    ): PromptDao = database.promptDao()

    @Provides
    fun providePublicModelDao(
        database: ImageFactoryDatabase
    ): PublicModelDao = database.publicModelDao()

    @Provides
    fun provideTxtToImgHistoryDao(
        database: ImageFactoryDatabase
    ): TxtToImgHistoryDao = database.txtToImgHistoryDao()
}
