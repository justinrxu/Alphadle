package com.alphadle.domain.repository

import com.alphadle.data.dao.WordStatsDao
import com.alphadle.data.entity.WordStats
import com.alphadle.domain.repository.interfaces.IWordStatsRepository

internal class WordStatsRepositoryImpl(
    private val wordStatsDao: WordStatsDao
) : IWordStatsRepository {
    override suspend fun upsertWordStats(wordStats: WordStats) {
        wordStatsDao.upsertWordStats(wordStats)
    }

    override suspend fun getWordStats(): List<WordStats> {
        return wordStatsDao.getAllWordStats()
    }
}