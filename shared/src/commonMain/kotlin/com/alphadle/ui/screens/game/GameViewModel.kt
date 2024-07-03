package com.alphadle.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphadle.data.entity.DailyGuesses
import com.alphadle.domain.exceptions.InvalidGuessException
import com.alphadle.domain.model.GameData
import com.alphadle.domain.usecase.SubmitGuessUseCase
import com.alphadle.domain.usecase.GetSavedGameDataUseCase
import com.alphadle.domain.usecase.SubmitGameDataForWordStatsUseCase
import com.alphadle.domain.usecase.ValidateGuessUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

internal class GameViewModel(
    difficulty: GameData.Difficulty,
    getSavedGameDataUseCase: GetSavedGameDataUseCase,
    private val validateGuessUseCase: ValidateGuessUseCase,
    private val submitGuessUseCase: SubmitGuessUseCase,
    private val submitGameDataForWordStatsUseCase: SubmitGameDataForWordStatsUseCase,
) : ViewModel() {
    val savedGameData: StateFlow<GameData> = with(getSavedGameDataUseCase.invoke(difficulty)) {
        onEach { gameData ->
            if (gameData.completed) {
                viewModelScope.launch {
                    submitGameDataForWordStatsUseCase.invoke(gameData)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = runBlocking { first() }
        )
    }

    fun validateGuess(
        guess: String,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                validateGuessUseCase.invoke(guess, savedGameData.value)
                onSuccess()
            } catch (e: InvalidGuessException) {
                onError(e.message)
            }
        }
    }

    fun submitGuess(
        guess: String,
        onError: (String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                submitGuessUseCase.invoke(guess, savedGameData.value)
            } catch (e: InvalidGuessException) {
                onError(e.message)
            }
        }
    }
}
