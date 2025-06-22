package com.medzup.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medzup.app.data.database.model.PatientEntity
import com.medzup.app.data.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MedicineRepository
) : ViewModel() {
    val patients: StateFlow<List<PatientEntity>> = repository.getAllPatients()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addPatient(name: String) {
        if (name.isNotBlank()) {
            viewModelScope.launch {
                repository.addPatient(PatientEntity(name = name))
            }
        }
    }
} 