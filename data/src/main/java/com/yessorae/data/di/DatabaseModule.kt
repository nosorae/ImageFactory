package com.yessorae.data.di

import android.content.Context
import androidx.room.Room
import com.yessorae.data.local.database.ImageFactoryDatabase
import com.yessorae.data.util.DBConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideImageFactoryDatabase(
        @ApplicationContext context: Context
    ): ImageFactoryDatabase = Room.databaseBuilder(
        context = context,
        klass = ImageFactoryDatabase::class.java,
        name = DBConstants.DATABASE_NAME
    ).build()
}
