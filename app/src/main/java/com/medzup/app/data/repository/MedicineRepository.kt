package com.medzup.app.data.repository

import com.medzup.app.data.database.dao.DoseHistoryDao
import com.medzup.app.data.database.dao.MedicineDao
import com.medzup.app.data.database.dao.PatientDao
import com.medzup.app.data.database.model.DoseHistoryEntity
import com.medzup.app.data.database.model.MedicineEntity
import com.medzup.app.data.database.model.PatientEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicineRepository @Inject constructor(
    private val medicineDao: MedicineDao,
    private val doseHistoryDao: DoseHistoryDao,
    private val patientDao: PatientDao
) {

    // Patient Functions
    fun getAllPatients(): Flow<List<PatientEntity>> = patientDao.getAllPatients()
    suspend fun addPatient(patient: PatientEntity) = patientDao.insert(patient)
    fun getPatientById(patientId: Long): Flow<PatientEntity?> = patientDao.getPatientById(patientId)
    suspend fun deletePatient(patientId: Long) = patientDao.deleteById(patientId)

    // Medicine Functions
    fun getMedicinesForPatient(patientId: Long): Flow<List<MedicineEntity>> = medicineDao.getMedicinesForPatient(patientId)
    fun getMedicineById(medicineId: Long): Flow<MedicineEntity?> = medicineDao.getMedicineById(medicineId)
    suspend fun addMedicine(medicine: MedicineEntity): Long = medicineDao.insert(medicine)
    suspend fun updateMedicine(medicine: MedicineEntity) = medicineDao.update(medicine)
    suspend fun deleteMedicine(medicineId: Long) = medicineDao.deleteById(medicineId)

    // Dose History Functions
    fun getDoseHistoryForMedicine(medicineId: Long): Flow<List<DoseHistoryEntity>> = doseHistoryDao.getDoseHistoryForMedicine(medicineId)
    suspend fun addDoseHistory(doseHistory: DoseHistoryEntity) = doseHistoryDao.insertDoseHistory(doseHistory)
} 