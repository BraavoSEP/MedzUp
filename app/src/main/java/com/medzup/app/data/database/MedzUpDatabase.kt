package com.medzup.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.medzup.app.data.database.dao.DoseHistoryDao
import com.medzup.app.data.database.dao.MedicineDao
import com.medzup.app.data.database.model.DoseHistoryEntity
import com.medzup.app.data.database.model.MedicineEntity

@Database(
    entities = [MedicineEntity::class, DoseHistoryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(com.medzup.app.data.database.TypeConverters::class)
abstract class MedzUpDatabase : RoomDatabase() {

    abstract fun medicineDao(): MedicineDao
    abstract fun doseHistoryDao(): DoseHistoryDao

    companion object {
        const val DATABASE_NAME = "medzup_db"
    }
} 