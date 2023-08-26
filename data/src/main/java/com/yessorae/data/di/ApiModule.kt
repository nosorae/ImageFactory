package com.yessorae.data.di

import com.yessorae.data.api.TxtToImgApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object ApiModule {
    @Provides
    fun provideTxtToImgApi(retrofit: Retrofit): TxtToImgApi {
        return retrofit.create(TxtToImgApi::class.java)
    }
}
