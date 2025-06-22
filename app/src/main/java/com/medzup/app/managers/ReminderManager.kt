package com.medzup.app.managers

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.medzup.app.data.database.model.MedicineEntity
import com.medzup.app.workers.ReminderWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val workManager = WorkManager.getInstance(context)

    fun scheduleReminders(medicine: MedicineEntity) {
        cancelReminders(medicine.id)

        try {
            val timeParts = medicine.startTime.split(":").map { it.toInt() }
            if (timeParts.size != 2) return // Invalid time format

            val startHour = timeParts[0]
            val startMinute = timeParts[1]
            val interval = medicine.intervalHours
            val duration = medicine.durationDays

            val now = Calendar.getInstance()
            val treatmentEnd = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, duration) }

            val currentReminderTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, startHour)
                set(Calendar.MINUTE, startMinute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            // If the very first reminder time is in the past, move it to the next day
            if (currentReminderTime.before(now)) {
                currentReminderTime.add(Calendar.DAY_OF_YEAR, 1)
            }

            var reminderIndex = 0
            while (currentReminderTime.before(treatmentEnd)) {
                val delay = currentReminderTime.timeInMillis - now.timeInMillis
                
                // Only schedule if the reminder is in the future
                if (delay > 0) {
                    val data = workDataOf(
                        ReminderWorker.KEY_MEDICINE_NAME to medicine.name,
                        ReminderWorker.KEY_DOSAGE to medicine.dosage
                    )
                    
                    val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                        .setInputData(data)
                        .addTag("reminder_${medicine.id}")
                        .build()
                    
                    workManager.enqueueUniqueWork(
                        "reminder_${medicine.id}_${reminderIndex}",
                        ExistingWorkPolicy.REPLACE,
                        workRequest
                    )
                    reminderIndex++
                }
                
                // Move to the next reminder time
                currentReminderTime.add(Calendar.HOUR_OF_DAY, interval)
            }

        } catch (e: Exception) {
            // Log error in a real app
            e.printStackTrace()
        }
    }

    fun cancelReminders(medicineId: Long) {
        workManager.cancelAllWorkByTag("reminder_$medicineId")
    }
} 