package com.medzup.app.data.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "medicines",
    foreignKeys = [
        ForeignKey(
            entity = PatientEntity::class,
            parentColumns = ["id"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["patientId"])]
)
data class MedicineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val patientId: Long,
    val name: String,
    val dosage: String, // e.g., "1 pill", "10ml"
    val stock: Int,
    val startTime: String,      // e.g., "08:00"
    val intervalHours: Int,   // e.g., 8
    val durationDays: Int       // e.g., 7
) 