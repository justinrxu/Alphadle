package com.alphadle.domain.usecase

import co.touchlab.kermit.Logger
import com.alphadle.domain.repository.interfaces.IDailyGuessesRepository
import kotlinx.coroutines.flow.first

internal class SubmitGuessUseCase(
    private val dailyGuessesRepository: IDailyGuessesRepository
) {
    suspend operator fun invoke(guess: String) {
        Logger.i { "Submitting guess to local game data..." }
        dailyGuessesRepository.getDailyGuesses().first().let { dailyGuesses ->
            if (guess.length != dailyGuesses.answer.length) {
                Logger.w { "Submitted guess is not the same length as answer!" }
            }
            dailyGuessesRepository.updateDailyGuesses(
                with(dailyGuesses) {
                    copy(
                        guesses = guesses + guess,
                        answer = answer,
                        date = date
                    )
                }
            )
        }
    }
}