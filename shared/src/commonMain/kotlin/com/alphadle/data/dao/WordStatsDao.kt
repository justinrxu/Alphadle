package com.alphadle.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.alphadle.data.entity.WordStats

@Dao
internal interface WordStatsDao {
    @Upsert
    suspend fun upsertWordStats(wordStats: WordStats)

    @Query("SELECT * FROM WordStats")
    suspend fun getAllWordStats(): List<WordStats>
}