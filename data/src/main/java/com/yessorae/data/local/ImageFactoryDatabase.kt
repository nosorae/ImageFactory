package com.yessorae.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yessorae.data.local.dao.PromptDao
import com.yessorae.data.local.model.PromptEntity

@Database(entities = [PromptEntity::class], version = 1)
abstract class ImageFactoryDatabase : RoomDatabase() {
    abstract fun promptDao(): PromptDao
}