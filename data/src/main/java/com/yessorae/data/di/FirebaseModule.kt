package com.yessorae.data.di

import com.yessorae.data.remote.firebase.FireStorageService
import com.yessorae.data.remote.firebase.FireStorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FirebaseModule {
    @Binds
    fun provideFireStorageService(fireStorageServiceImpl: FireStorageServiceImpl): FireStorageService
}