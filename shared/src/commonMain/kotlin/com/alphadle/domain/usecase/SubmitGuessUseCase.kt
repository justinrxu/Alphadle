package com.alphadle.domain.usecase

import co.touchlab.kermit.Logger
import com.alphadle.data.entity.DailyGuesses
import com.alphadle.domain.model.GameData
import com.alphadle.domain.repository.interfaces.IDailyGuessesRepository

internal class SubmitGuessUseCase(
    private val validateGuessUseCase: ValidateGuessUseCase,
    private val dailyGuessesRepository: IDailyGuessesRepository,
) {
    suspend operator fun invoke(guess: String, gameData: GameData) {
        Logger.i { "Submitting guess to local game data..." }
        validateGuessUseCase.invoke(guess, gameData)
        dailyGuessesRepository.upsertDailyGuesses(
            with(gameData) {
                DailyGuesses(
                    date = date,
                    difficulty = difficulty.name,
                    guesses = guesses + guess,
                    answer = answer
                )
            }
        )
    }
}