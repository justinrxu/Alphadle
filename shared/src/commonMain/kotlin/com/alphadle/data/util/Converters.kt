package com.alphadle.data.util

import androidx.room.TypeConverter
import com.alphadle.domain.model.GameData
import kotlinx.datetime.LocalDate

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
    fun difficultyToString(value: GameData.Difficulty): String {
        return value.name
    }

    @TypeConverter
    fun stringToDifficulty(value: String): GameData.Difficulty {
        return enumValueOf<GameData.Difficulty>(value)
    }
}