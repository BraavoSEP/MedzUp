package com.medzup.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.medzup.app.data.database.model.MedicineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicine: MedicineEntity): Long

    @Update
    suspend fun updateMedicine(medicine: MedicineEntity)

    @Query("DELETE FROM medicines WHERE id = :medicineId")
    suspend fun deleteMedicine(medicineId: Long)

    @Query("SELECT * FROM medicines WHERE id = :medicineId")
    fun getMedicineById(medicineId: Long): Flow<MedicineEntity?>

    @Query("SELECT * FROM medicines ORDER BY name ASC")
    fun getAllMedicines(): Flow<List<MedicineEntity>>
} 