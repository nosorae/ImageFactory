package com.yessorae.data.di

import com.yessorae.domain.repository.ImageUploadRepository
import com.yessorae.data.repository.ImageEditingRepositoryImpl
import com.yessorae.data.repository.ImageUploadRepositoryImpl
import com.yessorae.data.repository.PromptRepositoryImpl
import com.yessorae.data.repository.PublicModelRepositoryImpl
import com.yessorae.data.repository.TxtToImgHistoryRepositoryImpl
import com.yessorae.data.repository.TxtToImgRepositoryImpl
import com.yessorae.domain.repository.PromptRepository
import com.yessorae.domain.repository.PublicModelRepository
import com.yessorae.domain.repository.TxtToImgRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideImageEditingRepository(imageEditingRepository: ImageEditingRepositoryImpl): ImageUploadRepository

    @Binds
    fun provideImageUploadRepository(imageUploadRepository: ImageUploadRepositoryImpl): ImageUploadRepository

    @Binds
    fun providePromptRepositoryImpl(promptRepository: PromptRepositoryImpl): PromptRepository

    @Binds
    fun providePublicModelRepository(publicModelRepository: PublicModelRepositoryImpl): PublicModelRepository

    @Binds
    fun provideTxtToImgHistoryRepository(txtToImgHistoryRepository: TxtToImgHistoryRepositoryImpl): TxtToImgHistoryRepositoryImpl

    @Binds
    fun provideTxtTopImgRepository(txtToImgRepository: TxtToImgRepositoryImpl): TxtToImgRepository
}
