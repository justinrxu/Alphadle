package com.alphadle.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.alphadle.data.entity.DailyGuesses
import kotlinx.coroutines.flow.Flow

@Dao
internal interface DailyGuessesDao {
    @Upsert
    suspend fun upsertDailyGuesses(dailyGuesses: DailyGuesses)

    @Query("SELECT * FROM DailyGuesses")
    suspend fun getAllDailyGuesses(): List<DailyGuesses>

    @Query("SELECT * FROM DailyGuesses")
    fun getAllDailyGuessesAsFlow(): Flow<List<DailyGuesses>>

    @Query("DELETE FROM DailyGuesses")
    suspend fun deleteAllDailyGuesses()
}