package com.alphadle.data.entity

import androidx.room.Entity
import kotlinx.datetime.LocalDate

@Entity(primaryKeys = ["date", "difficulty"])
internal data class DailyGuesses(
    val date: LocalDate,
    val difficulty: String,
    val guesses: List<String> = emptyList(),
    val answer: String
)