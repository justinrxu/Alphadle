package com.alphadle.domain.repository.interfaces

import com.alphadle.data.entity.WordStats

internal interface IWordStatsRepository {
    suspend fun upsertWordStats(wordStats: WordStats)

    suspend fun getWordStats(): List<WordStats>
}