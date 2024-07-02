package com.alphadle.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphadle.domain.model.GameData
import com.alphadle.domain.usecase.SubmitGuessUseCase
import com.alphadle.domain.usecase.GetSavedGameDataUseCase
import com.alphadle.domain.usecase.SubmitGameDataForWordStatsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

internal class GameViewModel(
    private val getSavedGameDataUseCase: GetSavedGameDataUseCase,
    private val submitGuessUseCase: SubmitGuessUseCase,
    private val submitGameDataForWordStatsUseCase: SubmitGameDataForWordStatsUseCase
) : ViewModel() {
    private val _savedGameDataFlow = getSavedGameDataUseCase.invoke()
    val savedGameData: StateFlow<GameData> = _savedGameDataFlow.onEach { gameData ->
        if (gameData.completed) {
            viewModelScope.launch {
                submitGameDataForWordStatsUseCase.invoke()
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = runBlocking { _savedGameDataFlow.first() }
    )

    fun submitGuess(guess: String) {
        viewModelScope.launch {
            submitGuessUseCase.invoke(guess)
        }
    }
}