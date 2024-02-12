package com.yessorae.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yessorae.data.local.database.converter.BitmapConverter
import com.yessorae.data.local.database.converter.ListStringConverter
import com.yessorae.data.local.database.converter.LocalDateTimeConverter
import com.yessorae.data.local.database.dao.EmbeddingsModelDao
import com.yessorae.data.local.database.dao.LoRaModelDao
import com.yessorae.data.local.database.dao.PromptDao
import com.yessorae.data.local.database.dao.PublicModelDao
import com.yessorae.data.local.database.dao.SDModelDao
import com.yessorae.data.local.database.dao.TxtToImgHistoryDao
import com.yessorae.data.local.database.dao.TxtToImgUpscaleHistoryDao
import com.yessorae.data.local.database.model.EmbeddingsModelEntity
import com.yessorae.data.local.database.model.LoRaModelEntity
import com.yessorae.data.local.database.model.PromptEntity
import com.yessorae.data.local.database.model.PublicModelEntity
import com.yessorae.data.local.database.model.SDModelEntity
import com.yessorae.data.local.database.model.TxtToImgEntity
import com.yessorae.data.local.database.model.TxtToImgUpscaleHistoryEntity

@Database(
    entities = [
        EmbeddingsModelEntity::class,
        LoRaModelEntity::class,
        PromptEntity::class,
        PublicModelEntity::class,
        SDModelEntity::class,
        TxtToImgEntity::class,
        TxtToImgUpscaleHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    LocalDateTimeConverter::class,
    ListStringConverter::class,
    BitmapConverter::class
)
abstract class ImageFactoryDatabase : RoomDatabase() {
    abstract fun promptDao(): PromptDao
    abstract fun publicModelDao(): PublicModelDao
    abstract fun sdModelDao(): SDModelDao
    abstract fun loRaModelDao(): LoRaModelDao
    abstract fun embeddingsModelDao(): EmbeddingsModelDao
    abstract fun txtToImgHistoryDao(): TxtToImgHistoryDao
    abstract fun txtToImgUpscaleHistoryDao(): TxtToImgUpscaleHistoryDao
}
