package com.alphadle.data.entity

import kotlinx.datetime.LocalDate

internal data class DailyGuesses(
    val guesses: List<String> = emptyList(),
    val answer: String,
    val date: LocalDate
)