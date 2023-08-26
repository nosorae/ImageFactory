package com.yessorae.data.di

import com.common.DataConstants
import com.yessorae.common.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

            .addInterceptor(
                HttpLoggingInterceptor { message ->
                    Logger.network(message = message)
                }.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                    redactHeader("Authorization")
                    redactHeader("Cookie")
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideStableDiffusionAPIRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DataConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }
}