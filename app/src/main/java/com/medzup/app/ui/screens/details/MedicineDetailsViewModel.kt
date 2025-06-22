package com.medzup.app.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medzup.app.data.database.model.DoseHistoryEntity
import com.medzup.app.data.database.model.MedicineEntity
import com.medzup.app.data.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class MedicineDetailsViewModel @Inject constructor(
    private val repository: MedicineRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val medicineId: Long = savedStateHandle.get<String>("medicineId")?.toLongOrNull() ?: 0

    val medicine: StateFlow<MedicineEntity?> = repository.getMedicineById(medicineId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val doseHistory: StateFlow<List<DoseHistoryEntity>> = repository.getDoseHistoryForMedicine(medicineId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _simplifiedBula = MutableStateFlow<String?>(null)
    val simplifiedBula: StateFlow<String?> = _simplifiedBula

    fun simplifyBulaText(fullText: String) {
        viewModelScope.launch {
            // Simulate a network call to an AI service
            _simplifiedBula.value = "Simplifying..."
            delay(2000) // 2-second delay
            
            // Placeholder for actual AI summarization logic
            _simplifiedBula.value = """
                **Simplified Summary:**
                
                - **What is it for?** Helps with [Main Purpose].
                - **How to take?** [Dosage] times a day.
                - **Common side effects:** May cause [Side Effect 1] or [Side Effect 2].
                - **Warning:** Do not take if you are [Contraindication].
                
                (This is a sample summary. The real implementation would use AI.)
            """.trimIndent()
        }
    }

    fun recordDose(status: String) {
        viewModelScope.launch {
            val dose = DoseHistoryEntity(
                medicineId = medicineId,
                timestamp = System.currentTimeMillis(),
                status = status
            )
            repository.addDoseHistory(dose)
            // Also decrement stock if the dose was taken
            if (status == "TAKEN") {
                medicine.value?.let {
                    if (it.stock > 0) {
                        repository.updateMedicine(it.copy(stock = it.stock - 1))
                    }
                }
            }
        }
    }
} 