package com.alphadle.ui.screens.end

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphadle.data.entity.WordStats
import com.alphadle.domain.model.GameData
import com.alphadle.domain.model.TotalStats
import com.alphadle.domain.usecase.GetAllWordStatsUseCase
import com.alphadle.domain.usecase.GetSavedGameDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

internal class EndViewModel(
    private val getSavedGameDataUseCase: GetSavedGameDataUseCase,
    private val getAllWordStatsUseCase: GetAllWordStatsUseCase
) : ViewModel() {
    sealed class EndUiState {
        data object Loading : EndUiState()
        data class Loaded(
            val savedGameData: GameData,
            val totalStats: TotalStats
        ): EndUiState()
    }

    private val _endUiState: MutableStateFlow<EndUiState> = MutableStateFlow(EndUiState.Loading)
    val endUiState: StateFlow<EndUiState> = _endUiState

    init {
        viewModelScope.launch {
            _endUiState.value = EndUiState.Loaded(
                savedGameData = getSavedGameDataUseCase.invoke().first(),
                totalStats = getAllWordStatsUseCase.invoke()
            )
        }
    }
}