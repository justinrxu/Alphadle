package com.alphadle.domain.usecase

import co.touchlab.kermit.Logger
import com.alphadle.domain.model.GameData
import com.alphadle.domain.model.GameData.Difficulty
import com.alphadle.domain.repository.interfaces.IDailyGuessesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetSavedGameDataUseCase(
    private val dailyGuessesRepository: IDailyGuessesRepository,
) {
    operator fun invoke(difficulty: Difficulty): Flow<GameData> {
        Logger.i { "Loading saved game data..." }
        return dailyGuessesRepository.getDailyGuessesByDifficultyAsFlow(difficulty.name)
            .map { dailyGuesses ->
                if (dailyGuesses == null) {
                    throw Exception()
                }
                GameData(
                    guesses = dailyGuesses.guesses,
                    answer = dailyGuesses.answer,
                    date = dailyGuesses.date,
                    difficulty = Difficulty.valueOf(dailyGuesses.difficulty)
                )
            }
    }
}