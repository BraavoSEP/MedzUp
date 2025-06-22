package com.medzup.app.ui.screens.patient_medicine_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medzup.app.data.database.model.MedicineEntity
import com.medzup.app.data.database.model.PatientEntity
import com.medzup.app.data.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PatientMedicineListViewModel @Inject constructor(
    private val repository: MedicineRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val patientId: Long = savedStateHandle.get<Long>("patientId") ?: 0

    val patient: StateFlow<PatientEntity?> = repository.getPatientById(patientId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val medicines: StateFlow<List<MedicineEntity>> = repository.getMedicinesForPatient(patientId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
} 