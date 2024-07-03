package com.alphadle.domain.repository.interfaces

import com.alphadle.domain.model.GameData

internal interface IWordListRepository {
    suspend fun getDailyWords(): Map<GameData.Difficulty, String>

    suspend fun isInWordList(guess: String): Boolean
}