package com.medzup.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.medzup.app.data.database.model.PatientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(patient: PatientEntity)

    @Query("SELECT * FROM patients ORDER BY name ASC")
    fun getAllPatients(): Flow<List<PatientEntity>>

    @Query("SELECT * FROM patients WHERE id = :patientId")
    fun getPatientById(patientId: Long): Flow<PatientEntity?>

    @Query("DELETE FROM patients WHERE id = :patientId")
    suspend fun deleteById(patientId: Long)
} 