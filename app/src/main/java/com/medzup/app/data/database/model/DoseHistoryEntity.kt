package com.medzup.app.data.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dose_history",
    foreignKeys = [
        ForeignKey(
            entity = MedicineEntity::class,
            parentColumns = ["id"],
            childColumns = ["medicineId"],
            onDelete = ForeignKey.CASCADE // If a medicine is deleted, its history is also deleted.
        )
    ],
    indices = [
        Index(value = ["medicineId"])
    ]
)
data class DoseHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val medicineId: Long,
    val timestamp: Long, // Store as Unix timestamp for easy sorting and filtering
    val status: String // e.g., "TAKEN", "SKIPPED"
) 