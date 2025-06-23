package com.medzup.app.ui.screens.add_medicine

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medzup.app.data.database.model.MedicineEntity
import com.medzup.app.data.repository.MedicineRepository
import com.medzup.app.managers.ReminderManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted

@HiltViewModel
class AddMedicineViewModel @Inject constructor(
    private val repository: MedicineRepository,
    private val reminderManager: ReminderManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val patientId: Long = savedStateHandle.get<Long>("patientId")!!
    private val medicineId: Long? = savedStateHandle.get<Long>("medicineId")

    val medicine: StateFlow<MedicineEntity?> = if (medicineId != null) {
        repository.getMedicineById(medicineId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    } else {
        MutableStateFlow(null)
    }

    fun saveMedicine(
        name: String,
        dosage: String,
        stock: String,
        startTime: String,
        intervalHours: String,
        durationDays: String,
        onSuccess: () -> Unit
    ) {
        if (name.isBlank() || dosage.isBlank() || stock.isBlank() || startTime.isBlank() || intervalHours.isBlank() || durationDays.isBlank()) {
            // TODO: Add user feedback for invalid input
            return
        }

        viewModelScope.launch {
            if (medicineId != null) {
                // Atualizar medicamento existente
                val updatedMedicine = MedicineEntity(
                    id = medicineId,
                    patientId = patientId,
                    name = name,
                    dosage = dosage,
                    stock = stock.toIntOrNull() ?: 0,
                    startTime = startTime,
                    intervalHours = intervalHours.toIntOrNull() ?: 24,
                    durationDays = durationDays.toIntOrNull() ?: 1
                )
                repository.updateMedicine(updatedMedicine)
                reminderManager.scheduleReminders(updatedMedicine)
                onSuccess()
            } else {
                // Novo cadastro
                val medicine = MedicineEntity(
                    patientId = patientId,
                    name = name,
                    dosage = dosage,
                    stock = stock.toIntOrNull() ?: 0,
                    startTime = startTime,
                    intervalHours = intervalHours.toIntOrNull() ?: 24,
                    durationDays = durationDays.toIntOrNull() ?: 1
                )
                val newId = repository.addMedicine(medicine)
                reminderManager.scheduleReminders(medicine.copy(id = newId))
                onSuccess()
            }
        }
    }
}
