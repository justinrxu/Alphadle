package com.alphadle.domain.di

import com.alphadle.data.database.AppDatabase
import com.alphadle.domain.repository.DailyGuessesRepositoryImpl
import com.alphadle.domain.repository.WordStatsRepositoryImpl
import com.alphadle.domain.repository.interfaces.IDailyGuessesRepository
import com.alphadle.domain.repository.interfaces.IWordStatsRepository
import com.alphadle.domain.usecase.GetAllWordStatsUseCase
import com.alphadle.domain.usecase.GetSavedGameDataUseCase
import com.alphadle.domain.usecase.PerformDailyResetUseCase
import com.alphadle.domain.usecase.SubmitGameDataForWordStatsUseCase
import com.alphadle.domain.usecase.SubmitGuessUseCase
import com.alphadle.ui.screens.end.EndViewModel
import com.alphadle.ui.screens.game.GameViewModel
import com.alphadle.ui.screens.start.TitleViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelsModule = module {
    viewModel { TitleViewModel(get()) }
    viewModel { GameViewModel(get(), get(), get()) }
    viewModel { EndViewModel(get(), get()) }
}

internal val useCasesViewModel = module {
    single { PerformDailyResetUseCase(get()) }
    single { GetSavedGameDataUseCase(get()) }
    single { SubmitGuessUseCase(get()) }
    single { GetAllWordStatsUseCase(get()) }
    single { SubmitGameDataForWordStatsUseCase(get(), get()) }
}

internal val repositoriesModule = module {
    single<IDailyGuessesRepository> { DailyGuessesRepositoryImpl(get()) }
    single<IWordStatsRepository> { WordStatsRepositoryImpl(get<AppDatabase>().getWordStatsDao()) }
}