package com.yessorae.data.di

import com.yessorae.data.repository.ImageEditingRepositoryImpl
import com.yessorae.data.repository.ImageUploadRepositoryImpl
import com.yessorae.data.repository.InPaintingHistoryRepositoryImpl
import com.yessorae.data.repository.InPaintingRepositoryImpl
import com.yessorae.data.repository.ModelRepositoryImpl
import com.yessorae.data.repository.PreferenceRepositoryImpl
import com.yessorae.data.repository.PromptRepositoryImpl
import com.yessorae.data.repository.TxtToImgHistoryRepositoryImpl
import com.yessorae.data.repository.TxtToImgRepositoryImpl
import com.yessorae.domain.repository.ImageEditingRepository
import com.yessorae.domain.repository.ImageUploadRepository
import com.yessorae.domain.repository.inpainting.InPaintingHistoryRepository
import com.yessorae.domain.repository.inpainting.InPaintingRepository
import com.yessorae.domain.repository.ModelRepository
import com.yessorae.domain.repository.PreferenceRepository
import com.yessorae.domain.repository.PromptRepository
import com.yessorae.domain.repository.tti.TxtToImgHistoryRepository
import com.yessorae.domain.repository.tti.TxtToImgRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideImageEditingRepository(imageEditingRepository: ImageEditingRepositoryImpl): ImageEditingRepository

    @Binds
    fun provideImageUploadRepository(imageUploadRepository: ImageUploadRepositoryImpl): ImageUploadRepository

    @Binds
    fun provideModelRepository(modelRepository: ModelRepositoryImpl): ModelRepository

    @Binds
    fun providePreferenceRepository(preferenceRepository: PreferenceRepositoryImpl): PreferenceRepository

    @Binds
    fun providePromptRepository(promptRepository: PromptRepositoryImpl): PromptRepository

    @Binds
    fun provideTxtToImgHistoryRepository(txtToImgHistoryRepository: TxtToImgHistoryRepositoryImpl): TxtToImgHistoryRepository

    @Binds
    fun provideTxtToImgRepository(txtToImgRepository: TxtToImgRepositoryImpl): TxtToImgRepository

    @Binds
    fun provideInPaintingHistoryRepository(inPaintingRepository: InPaintingHistoryRepositoryImpl): InPaintingHistoryRepository

    @Binds
    fun provideInPaintingRepository(inPaintingRepository: InPaintingRepositoryImpl): InPaintingRepository
}
