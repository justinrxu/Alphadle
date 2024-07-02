package com.alphadle.domain.model

internal data class TotalStats(
    val gamesPlayed: Int,
    val gamesWon: Int,
    val currentStreak: Int,
    val distribution: Map<GameData.Difficulty, List<Int>>
)