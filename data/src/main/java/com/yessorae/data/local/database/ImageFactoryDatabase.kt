package com.yessorae.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yessorae.data.local.database.converter.LocalDateTimeConverter
import com.yessorae.data.local.database.dao.PromptDao
import com.yessorae.data.local.database.dao.PublicModelDao
import com.yessorae.data.local.database.model.PromptEntity
import com.yessorae.data.local.database.model.PublicModelEntity

@Database(
    entities = [PromptEntity::class, PublicModelEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class ImageFactoryDatabase : RoomDatabase() {
    abstract fun promptDao(): PromptDao
    abstract fun publicModelDao(): PublicModelDao
}
