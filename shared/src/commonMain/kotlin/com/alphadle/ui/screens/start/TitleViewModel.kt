package com.alphadle.ui.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphadle.domain.usecase.PerformDailyResetUseCase
import com.alphadle.domain.util.currentGameDate
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

internal class TitleViewModel(
    private val performDailyResetUseCase: PerformDailyResetUseCase
) : ViewModel() {
    val date: LocalDate
        get() = performDailyResetUseCase.getCurrentGameDate()

    fun checkDailyReset() {
        viewModelScope.launch {
            performDailyResetUseCase.invoke()
        }
    }
}