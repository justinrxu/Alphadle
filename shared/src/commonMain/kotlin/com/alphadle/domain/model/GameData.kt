package com.alphadle.domain.model

import kotlinx.datetime.LocalDate
import kotlin.math.max

internal data class GameData(
    val guesses: List<String>,
    val answer: String,
    val date: LocalDate
) {
    enum class Difficulty(val wordLength: Int, val attempts: Int) {
        NORMAL(6, 6)
    }

    val difficulty: Difficulty =
        Difficulty.entries.find { it.wordLength == answer.length }
            ?: Difficulty.NORMAL

    val attempts: Int
        get() = guesses.size
    val remainingAttempts: Int
        get() = max(difficulty.attempts - attempts, 0)
    val didWin: Boolean
        get() = guesses.any { it == answer }
    val completed: Boolean
        get() = didWin || remainingAttempts == 0
}