package com.alphadle.domain.usecase

import co.touchlab.kermit.Logger
import com.alphadle.data.entity.DailyGuesses
import com.alphadle.domain.repository.interfaces.IDailyGuessesRepository
import com.alphadle.domain.repository.interfaces.IWordListRepository
import com.alphadle.domain.util.currentGameDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

internal class PerformDailyResetUseCase(
    private val dailyGuessesRepository: IDailyGuessesRepository,
    private val wordListRepository: IWordListRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.Default) {
        val date = getCurrentGameDate()
        Logger.i { "Checking Daily Guesses for $date..." }
        if (dailyGuessesRepository.getAllDailyGuesses().all { it.date != date }) {
            Logger.i { "Retrieving new words for today..." }
            dailyGuessesRepository.deleteAllDailyGuesses()
            wordListRepository.deleteDailyWords()
            wordListRepository.getDailyWords()
                .also { wordMap ->
                    Logger.i { "Today's words are ${wordMap.values}" }
                }.forEach { (difficulty, word) ->
                    dailyGuessesRepository.upsertDailyGuesses(
                        DailyGuesses(
                            date = date,
                            difficulty = difficulty.name,
                            answer = word.uppercase()
                        )
                    )
                }
        }
    }

    fun getCurrentGameDate(): LocalDate = currentGameDate()
}