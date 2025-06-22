package com.medzup.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.medzup.app.data.database.model.DoseHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DoseHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoseHistory(doseHistory: DoseHistoryEntity)

    @Query("SELECT * FROM dose_history WHERE medicineId = :medicineId ORDER BY timestamp DESC")
    fun getDoseHistoryForMedicine(medicineId: Long): Flow<List<DoseHistoryEntity>>

    @Query("SELECT * FROM dose_history ORDER BY timestamp DESC")
    fun getAllDoseHistory(): Flow<List<DoseHistoryEntity>>
} 