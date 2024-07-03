package com.alphadle.data.entity

import androidx.room.Entity
import kotlinx.datetime.LocalDate

@Entity(primaryKeys = ["date", "difficulty"])
internal data class WordStats(
    val date: LocalDate,
    val difficulty: String,
    val didWin: Boolean,
    val attempts: Int
)