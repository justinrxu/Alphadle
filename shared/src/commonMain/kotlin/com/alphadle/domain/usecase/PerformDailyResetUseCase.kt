package com.alphadle.domain.usecase

import co.touchlab.kermit.Logger
import com.alphadle.data.entity.DailyGuesses
import com.alphadle.domain.repository.interfaces.IDailyGuessesRepository
import com.alphadle.domain.repository.interfaces.IWordListRepository
import com.alphadle.domain.util.currentGameDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class PerformDailyResetUseCase(
    private val dailyGuessesRepository: IDailyGuessesRepository,
    private val wordListRepository: IWordListRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.Default) {
        dailyGuessesRepository.deleteAllDailyGuesses()
        val currentGameDate = currentGameDate()
        Logger.i { "Checking Daily Guesses for $currentGameDate..." }
        if (dailyGuessesRepository.getAllDailyGuesses().all { it.date != currentGameDate }) {
            Logger.i { "Retrieving word of the day..." }
            dailyGuessesRepository.deleteAllDailyGuesses()
            wordListRepository.getDailyWords().forEach { (difficulty, word) ->
                dailyGuessesRepository.upsertDailyGuesses(
                    DailyGuesses(
                        date = currentGameDate,
                        difficulty = difficulty.name,
                        answer = word
                    )
                )
            }
        }
    }
}