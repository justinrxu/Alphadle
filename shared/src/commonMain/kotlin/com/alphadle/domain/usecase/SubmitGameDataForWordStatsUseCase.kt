package com.alphadle.domain.usecase

import co.touchlab.kermit.Logger
import com.alphadle.data.entity.WordStats
import com.alphadle.domain.model.GameData
import com.alphadle.domain.repository.interfaces.IWordStatsRepository
import kotlinx.coroutines.flow.first

internal class SubmitGameDataForWordStatsUseCase(
    private val wordStatsRepository: IWordStatsRepository,
) {
    suspend operator fun invoke(gameData: GameData) {
        Logger.i { "Converting completed local game data to word stats..." }
        with(gameData) {
            if (!completed) {
                Logger.w { "Local game data is not complete!" }
                TODO("Throw exception")
            }

            wordStatsRepository.upsertWordStats(
                WordStats(
                    didWin = didWin,
                    difficulty = difficulty.name,
                    attempts = guesses.size,
                    date = date
                )
            )
        }
    }
}