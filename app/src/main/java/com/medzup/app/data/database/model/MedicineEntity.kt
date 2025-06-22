package com.medzup.app.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class MedicineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val dosage: String, // e.g., "1 pill", "10ml"
    val stock: Int,
    // We can store a list of reminder times in a simple format,
    // for more complex schedules, a separate table might be better.
    val reminderTimes: String, // e.g., "08:00,20:00"
    val bulaInfo: String? = null // For simplified bula text
) 