package com.alphadle.domain.repository.interfaces

import com.alphadle.data.entity.DailyGuesses
import kotlinx.coroutines.flow.Flow

internal interface IDailyGuessesRepository {
    suspend fun upsertDailyGuesses(dailyGuesses: DailyGuesses)

    suspend fun getAllDailyGuesses(): List<DailyGuesses>
    fun getDailyGuessesByDifficultyAsFlow(difficulty: String): Flow<DailyGuesses?>

    suspend fun deleteAllDailyGuesses()
}