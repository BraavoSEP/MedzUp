package com.medzup.app.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.medzup.app.R
import java.util.concurrent.TimeUnit

class ReminderWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val medicineName = inputData.getString(KEY_MEDICINE_NAME) ?: return Result.failure()
        val dosage = inputData.getString(KEY_DOSAGE) ?: return Result.failure()

        showNotification(medicineName, dosage)
        // O reagendamento agora é feito inteiramente pelo ReminderManager
        // ao salvar o medicamento, para toda a duração do tratamento.
        // rescheduleNextWork() // REMOVED

        return Result.success()
    }

    private fun showNotification(medicineName: String, dosage: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Medicine Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for medicine reminder notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Using our placeholder icon
            .setContentTitle("Time for your medicine!")
            .setContentText("It's time to take your $medicineName ($dosage).")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    companion object {
        const val KEY_MEDICINE_NAME = "key_medicine_name"
        const val KEY_DOSAGE = "key_dosage"
        private const val CHANNEL_ID = "medzup_reminder_channel"
    }
} 