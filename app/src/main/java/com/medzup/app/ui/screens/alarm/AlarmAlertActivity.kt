package com.medzup.app.ui.screens.alarm

import android.media.MediaPlayer
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.medzup.app.R
import android.media.AudioManager
import android.content.Context
import android.widget.Toast
import android.media.AudioAttributes

class AlarmAlertActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_alert)

        // Usar métodos modernos para mostrar sobre a tela de bloqueio e ligar a tela
        setShowWhenLocked(true)
        setTurnScreenOn(true)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val patientName = intent.getStringExtra("patient_name") ?: ""
        val medicineName = intent.getStringExtra("medicine_name") ?: ""

        findViewById<TextView>(R.id.tv_patient_name).text = "Paciente: $patientName"
        findViewById<TextView>(R.id.tv_medicine_name).text = "Medicamento: $medicineName"

        // Ajustar volume do alarme e usar canal correto
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(
            AudioManager.STREAM_ALARM,
            audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM),
            0
        )
        mediaPlayer = MediaPlayer.create(this, R.raw.alarme_medicamento)
        mediaPlayer?.setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
        if (mediaPlayer?.isPlaying == true) {
            Toast.makeText(this, "Som do alarme tocando!", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
} 