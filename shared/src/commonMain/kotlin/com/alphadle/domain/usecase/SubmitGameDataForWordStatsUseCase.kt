package com.alphadle.domain.usecase

import co.touchlab.kermit.Logger
import com.alphadle.data.entity.WordStats
import com.alphadle.domain.model.GameData
import com.alphadle.domain.repository.interfaces.IWordStatsRepository
import kotlinx.coroutines.flow.first

internal class SubmitGameDataForWordStatsUseCase(
    private val wordStatsRepository: IWordStatsRepository,
    private val getSavedGameDataUseCase: GetSavedGameDataUseCase
) {
    suspend operator fun invoke() {
        Logger.i { "Converting completed local game data to word stats..." }
        with(getSavedGameDataUseCase.invoke().first()) {
            if (!completed) {
                Logger.w { "Local game data is not complete!" }
                TODO("Throw exception")
            }

            wordStatsRepository.upsertWordStats(
                WordStats(
                    didWin = didWin,
                    difficulty = difficulty,
                    attempts = guesses.size,
                    date = date
                )
            )
        }
    }
}