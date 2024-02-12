package com.yessorae.data.di

import com.yessorae.data.local.database.ImageFactoryDatabase
import com.yessorae.data.local.database.dao.EmbeddingsModelDao
import com.yessorae.data.local.database.dao.LoRaModelDao
import com.yessorae.data.local.database.dao.PromptDao
import com.yessorae.data.local.database.dao.PublicModelDao
import com.yessorae.data.local.database.dao.SDModelDao
import com.yessorae.data.local.database.dao.TxtToImgHistoryDao
import com.yessorae.data.local.database.dao.TxtToImgUpscaleHistoryDao
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
    fun provideSDModelDao(
        database: ImageFactoryDatabase
    ): SDModelDao = database.sdModelDao()

    @Provides
    fun provideLoRaModelDao(
        database: ImageFactoryDatabase
    ): LoRaModelDao = database.loRaModelDao()

    @Provides
    fun provideEmbeddingsModelDao(
        database: ImageFactoryDatabase
    ): EmbeddingsModelDao = database.embeddingsModelDao()

    @Provides
    fun provideTxtToImgHistoryDao(
        database: ImageFactoryDatabase
    ): TxtToImgHistoryDao = database.txtToImgHistoryDao()

    @Provides
    fun provideTxtToImgUpscaleHistoryDao(
        database: ImageFactoryDatabase
    ): TxtToImgUpscaleHistoryDao = database.txtToImgUpscaleHistoryDao()
}
