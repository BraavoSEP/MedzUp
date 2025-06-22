package com.medzup.app.ui.screens.add_medicine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medzup.app.data.database.model.MedicineEntity
import com.medzup.app.data.repository.MedicineRepository
import com.medzup.app.managers.ReminderManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMedicineViewModel @Inject constructor(
    private val repository: MedicineRepository,
    private val reminderManager: ReminderManager
) : ViewModel() {

    fun addMedicine(
        name: String,
        dosage: String,
        stock: Int,
        reminderTimes: String
    ) {
        if (name.isBlank() || dosage.isBlank() || stock <= 0 || reminderTimes.isBlank()) {
            // Handle validation error in a real app (e.g., show a toast or a snackbar)
            return
        }

        viewModelScope.launch {
            val newMedicine = MedicineEntity(
                name = name,
                dosage = dosage,
                stock = stock,
                reminderTimes = reminderTimes
            )
            val newId = repository.addMedicine(newMedicine)
            
            // Schedule the reminders for the newly added medicine
            reminderManager.scheduleReminders(newMedicine.copy(id = newId))
        }
    }
} 