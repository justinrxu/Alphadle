package com.alphadle.domain.usecase

import co.touchlab.kermit.Logger
import com.alphadle.data.entity.WordStats
import com.alphadle.domain.model.GameData
import com.alphadle.domain.model.TotalStats
import com.alphadle.domain.repository.interfaces.IWordStatsRepository
import com.alphadle.domain.util.currentGameDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus

internal class GetAllWordStatsUseCase(
    private val wordStatsRepository: IWordStatsRepository
) {
    suspend operator fun invoke(): TotalStats {
        Logger.i { "Getting total word stats..." }

        return with(wordStatsRepository.getWordStats()) {
            TotalStats(
                gamesPlayed = size,
                gamesWon = count { it.didWin },
                currentStreak = getStreak(),
                distribution = getDistribution(),
            )
        }
    }

    private fun List<WordStats>.getStreak(): Int {
        val currentGameDate = currentGameDate()
        val dates = map { it.date }

        var count = 1
        while (dates.contains(currentGameDate.minus(count, DateTimeUnit.DAY))) {
            count += 1
        }

        return count
    }

    private fun List<WordStats>.getDistribution(): Map<GameData.Difficulty, List<Int>> {
        return GameData.Difficulty.entries.associateWith { difficulty ->
            (0..<difficulty.wordLength).map { i ->
                filter { it.difficulty == difficulty }
                    .count { it.attempts == i + 1 }
            }
        }
    }
}