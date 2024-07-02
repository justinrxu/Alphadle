package com.alphadle.ui.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphadle.domain.usecase.PerformDailyResetUseCase
import kotlinx.coroutines.launch

internal class TitleViewModel(
    private val performDailyResetUseCase: PerformDailyResetUseCase
) : ViewModel() {
    fun checkDailyReset() {
        viewModelScope.launch {
            performDailyResetUseCase.invoke()
        }
    }
}