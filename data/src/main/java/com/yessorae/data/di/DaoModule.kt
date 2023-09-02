package com.yessorae.data.di

import com.yessorae.data.local.ImageFactoryDatabase
import com.yessorae.data.local.dao.PromptDao
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
}