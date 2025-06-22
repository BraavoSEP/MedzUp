package com.medzup.app.data.database

import androidx.room.TypeConverter

class TypeConverters {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        return value?.split(",")?.map { it.trim() } ?: emptyList()
    }

    @TypeConverter
    fun fromList(list: List<String>?): String {
        return list?.joinToString(",") ?: ""
    }
} 