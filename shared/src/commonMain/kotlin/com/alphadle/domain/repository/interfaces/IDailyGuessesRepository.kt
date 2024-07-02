package com.alphadle.domain.repository.interfaces

import com.alphadle.data.entity.DailyGuesses
import kotlinx.coroutines.flow.Flow

internal interface IDailyGuessesRepository {
    fun getDailyGuesses(): Flow<DailyGuesses>

    suspend fun updateDailyGuesses(dailyGuesses: DailyGuesses)

    suspend fun deleteDailyGuesses()
}