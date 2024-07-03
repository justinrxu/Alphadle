package com.alphadle.domain.model

import com.alphadle.data.entity.DailyGuesses
import kotlinx.datetime.LocalDate
import kotlin.math.max

internal data class GameData(
    val guesses: List<String>,
    val answer: String,
    val date: LocalDate,
    val difficulty: Difficulty
) {
    companion object {
        fun fromDailyGuesses(dailyGuesses: DailyGuesses) = GameData(
            guesses = dailyGuesses.guesses,
            answer = dailyGuesses.answer,
            date = dailyGuesses.date,
            difficulty = Difficulty.valueOf(dailyGuesses.difficulty)
        )

        fun GameData.toDailyGuesses() = DailyGuesses(
            guesses = guesses,
            answer = answer,
            date = date,
            difficulty = difficulty.name
        )
    }
    enum class Difficulty(val wordLength: Int, val attempts: Int) {
        NORMAL(6, 6)
    }

    val attempts: Int
        get() = guesses.size
    val remainingAttempts: Int
        get() = max(difficulty.attempts - attempts, 0)
    val didWin: Boolean
        get() = guesses.any { it == answer }
    val completed: Boolean
        get() = didWin || remainingAttempts == 0
}