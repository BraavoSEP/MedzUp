package com.medzup.app.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.medzup.app.ui.screens.alarm.AlarmAlertActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val patientName = intent.getStringExtra("patient_name") ?: ""
        val medicineName = intent.getStringExtra("medicine_name") ?: ""
        val alertIntent = Intent(context, AlarmAlertActivity::class.java).apply {
            putExtra("patient_name", patientName)
            putExtra("medicine_name", medicineName)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        context.startActivity(alertIntent)
    }
} 