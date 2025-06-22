package com.medzup.app.data.repository

import com.medzup.app.data.database.dao.DoseHistoryDao
import com.medzup.app.data.database.dao.MedicineDao
import com.medzup.app.data.database.model.MedicineEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicineRepository @Inject constructor(
    private val medicineDao: MedicineDao,
    private val doseHistoryDao: DoseHistoryDao
) {

    fun getAllMedicines(): Flow<List<MedicineEntity>> {
        return medicineDao.getAllMedicines()
    }

    suspend fun addMedicine(medicine: MedicineEntity): Long {
        return medicineDao.insertMedicine(medicine)
    }

    suspend fun updateMedicine(medicine: MedicineEntity) {
        medicineDao.updateMedicine(medicine)
    }

    suspend fun deleteMedicine(medicineId: Long) {
        medicineDao.deleteMedicine(medicineId)
    }

    fun getMedicineById(medicineId: Long): Flow<MedicineEntity?> {
        return medicineDao.getMedicineById(medicineId)
    }

    fun getDoseHistoryForMedicine(medicineId: Long): Flow<List<com.medzup.app.data.database.model.DoseHistoryEntity>> {
        return doseHistoryDao.getDoseHistoryForMedicine(medicineId)
    }

    suspend fun addDoseHistory(doseHistory: com.medzup.app.data.database.model.DoseHistoryEntity) {
        doseHistoryDao.insertDoseHistory(doseHistory)
    }
} 