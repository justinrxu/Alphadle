package com.alphadle.domain.usecase

import co.touchlab.kermit.Logger
import com.alphadle.domain.exceptions.InvalidGuessException
import com.alphadle.domain.model.GameData
import com.alphadle.domain.repository.interfaces.IDailyGuessesRepository
import com.alphadle.domain.repository.interfaces.IWordListRepository

internal class ValidateGuessUseCase(
    private val wordListRepository: IWordListRepository
) {
    suspend operator fun invoke(guess: String, gameData: GameData) {
        with(gameData) {
            if (remainingAttempts == 0) {
                throw InvalidGuessException("No attempts remaining")
            }
            if (guess.length != difficulty.wordLength) {
                Logger.w { "Submitted guess is not the the correct length" }
                throw InvalidGuessException("Not enough letters")
            }
            if (!wordListRepository.isInWordList(guess)) {
                throw InvalidGuessException("Not in word list")
            }
        }
    }
}