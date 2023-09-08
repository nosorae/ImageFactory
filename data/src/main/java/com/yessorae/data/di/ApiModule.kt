package com.yessorae.data.di

import com.yessorae.data.remote.stablediffusion.api.ImageEditingApi
import com.yessorae.data.remote.stablediffusion.api.ModelListApi
import com.yessorae.data.remote.stablediffusion.api.TxtToImgApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideTxtToImgApi(retrofit: Retrofit): TxtToImgApi {
        return retrofit.create(TxtToImgApi::class.java)
    }

    @Provides
    fun provideModelListApi(retrofit: Retrofit): ModelListApi {
        return retrofit.create(ModelListApi::class.java)
    }

    @Provides
    fun provideImageEditingApi(retrofit: Retrofit): ImageEditingApi {
        return retrofit.create(ImageEditingApi::class.java)
    }
}
