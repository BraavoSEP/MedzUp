package com.medzup.app.di

import android.content.Context
import androidx.room.Room
import com.medzup.app.data.database.MedzUpDatabase
import com.medzup.app.data.database.dao.DoseHistoryDao
import com.medzup.app.data.database.dao.MedicineDao
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
    fun provideDatabase(@ApplicationContext context: Context): MedzUpDatabase {
        return Room.databaseBuilder(
            context,
            MedzUpDatabase::class.java,
            MedzUpDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideMedicineDao(database: MedzUpDatabase): MedicineDao {
        return database.medicineDao()
    }

    @Provides
    fun provideDoseHistoryDao(database: MedzUpDatabase): DoseHistoryDao {
        return database.doseHistoryDao()
    }
} 