package com.alphadle.domain.repository

import com.alphadle.data.dao.DailyGuessesDao
import com.alphadle.data.entity.DailyGuesses
import com.alphadle.domain.repository.interfaces.IDailyGuessesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DailyGuessesRepositoryImpl(
    private val dailyGuessesDao: DailyGuessesDao
) : IDailyGuessesRepository {
    override suspend fun getAllDailyGuesses(): List<DailyGuesses> {
        return dailyGuessesDao.getAllDailyGuesses()
    }

    override fun getDailyGuessesByDifficultyAsFlow(difficulty: String): Flow<DailyGuesses?> {
        return dailyGuessesDao.getAllDailyGuessesAsFlow().map { allDailyGuesses ->
            allDailyGuesses.find { dailyGuesses ->
                dailyGuesses.difficulty == difficulty
            }
        }
    }

    override suspend fun upsertDailyGuesses(dailyGuesses: DailyGuesses) {
        dailyGuessesDao.upsertDailyGuesses(dailyGuesses)
    }

    override suspend fun deleteAllDailyGuesses() {
        dailyGuessesDao.deleteAllDailyGuesses()
    }
}