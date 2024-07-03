package com.alphadle.data.util

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class Converters {
    @TypeConverter
    fun localDateToString(value: LocalDate): String {
        return value.toString()
    }

    @TypeConverter
    fun stringToLocalDate(value: String): LocalDate {
        return LocalDate.parse(value)
    }

    @TypeConverter
    fun stringListToString(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun stringToStringList(value: String): List<String> {
        return Json.decodeFromString(value)
    }
}