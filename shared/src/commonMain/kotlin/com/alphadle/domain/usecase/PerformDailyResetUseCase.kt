package com.alphadle.domain.usecase

import co.touchlab.kermit.Logger
import com.alphadle.domain.repository.interfaces.IDailyGuessesRepository
import com.alphadle.domain.util.currentGameDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

internal class PerformDailyResetUseCase(
    private val dailyGuessesRepository: IDailyGuessesRepository
) {
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    suspend operator fun invoke() = withContext(defaultDispatcher) {
        val currentGameDate = currentGameDate()
        Logger.i { "Checking Daily Guesses for $currentGameDate..." }
        if (dailyGuessesRepository.getDailyGuesses().first().date != currentGameDate) {
            Logger.i { "Retrieving word of the day..." }
            dailyGuessesRepository.deleteDailyGuesses()
        }
    }
}