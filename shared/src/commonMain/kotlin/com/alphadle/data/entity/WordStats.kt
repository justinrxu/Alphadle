package com.alphadle.data.entity

import androidx.room.Entity
import com.alphadle.domain.model.GameData
import kotlinx.datetime.LocalDate

@Entity(primaryKeys = ["date", "difficulty"])
internal data class WordStats(
    val date: LocalDate,
    val difficulty: GameData.Difficulty,
    val didWin: Boolean,
    val attempts: Int
)