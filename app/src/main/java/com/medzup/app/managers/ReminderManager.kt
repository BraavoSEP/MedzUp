package com.medzup.app.managers

import android.content.Context
import androidx.work.*
import com.medzup.app.data.database.model.MedicineEntity
import com.medzup.app.workers.ReminderWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val workManager = WorkManager.getInstance(context)

    fun scheduleReminders(medicine: MedicineEntity) {
        // First, cancel any existing reminders for this medicine to avoid duplicates
        cancelReminders(medicine.id)

        val reminderTimes = medicine.reminderTimes.split(",").map { it.trim() }

        reminderTimes.forEachIndexed { index, time ->
            val timeParts = time.split(":").map { it.toInt() }
            val hour = timeParts[0]
            val minute = timeParts[1]

            val now = Calendar.getInstance()
            val nextReminder = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
            }

            // If the time has already passed for today, schedule it for tomorrow
            if (nextReminder.before(now)) {
                nextReminder.add(Calendar.DAY_OF_YEAR, 1)
            }

            val initialDelay = nextReminder.timeInMillis - now.timeInMillis

            val data = workDataOf(
                ReminderWorker.KEY_MEDICINE_NAME to medicine.name,
                ReminderWorker.KEY_DOSAGE to medicine.dosage
            )
            
            val uniqueWorkName = "reminder_${medicine.id}_$index"

            val reminderRequest = PeriodicWorkRequestBuilder<ReminderWorker>(24, TimeUnit.HOURS)
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .addTag("reminder_${medicine.id}")
                .build()

            workManager.enqueueUniquePeriodicWork(
                uniqueWorkName,
                ExistingPeriodicWorkPolicy.REPLACE,
                reminderRequest
            )
        }
    }

    fun cancelReminders(medicineId: Long) {
        workManager.cancelAllWorkByTag("reminder_$medicineId")
    }
} 