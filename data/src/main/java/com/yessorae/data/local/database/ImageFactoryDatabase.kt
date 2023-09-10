package com.yessorae.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yessorae.data.local.database.converter.LocalDateTimeConverter
import com.yessorae.data.local.database.dao.PromptDao
import com.yessorae.data.local.database.model.PromptEntity

@Database(entities = [PromptEntity::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateTimeConverter::class)
abstract class ImageFactoryDatabase : RoomDatabase() {
    abstract fun promptDao(): PromptDao
}
