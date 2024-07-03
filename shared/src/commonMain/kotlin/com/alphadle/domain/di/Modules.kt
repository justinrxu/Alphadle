package com.alphadle.domain.di

import com.alphadle.data.database.AppDatabase
import com.alphadle.domain.repository.DailyGuessesRepositoryImpl
import com.alphadle.domain.repository.WordStatsRepositoryImpl
import com.alphadle.domain.repository.WordListRepositoryImpl
import com.alphadle.domain.repository.interfaces.IDailyGuessesRepository
import com.alphadle.domain.repository.interfaces.IWordStatsRepository
import com.alphadle.domain.repository.interfaces.IWordListRepository
import com.alphadle.domain.usecase.GetAllWordStatsUseCase
import com.alphadle.domain.usecase.GetSavedGameDataUseCase
import com.alphadle.domain.usecase.PerformDailyResetUseCase
import com.alphadle.domain.usecase.SubmitGameDataForWordStatsUseCase
import com.alphadle.domain.usecase.SubmitGuessUseCase
import com.alphadle.domain.usecase.ValidateGuessUseCase
import com.alphadle.ui.screens.end.EndViewModel
import com.alphadle.ui.screens.game.GameViewModel
import com.alphadle.ui.screens.start.TitleViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val httpClientModule = module {
    single {
        HttpClient(httpClientEngineFactory) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        prettyPrint = true
                    },
                    contentType = ContentType.Application.Json
                )
            }
        }
    }
}

internal val viewModelsModule = module {
    viewModel { TitleViewModel(get()) }
    viewModel { GameViewModel(get(), get(), get(), get(), get()) }
    viewModel { EndViewModel(get(), get(), get()) }
}

internal val useCasesViewModel = module {
    single { PerformDailyResetUseCase(get(), get()) }
    single { GetSavedGameDataUseCase(get()) }
    single { SubmitGuessUseCase(get(), get()) }
    single { GetAllWordStatsUseCase(get()) }
    single { SubmitGameDataForWordStatsUseCase(get()) }
    single { ValidateGuessUseCase(get()) }
}

internal val repositoriesModule = module {
    single<IDailyGuessesRepository> {
        DailyGuessesRepositoryImpl(get<AppDatabase>().getDailyGuessesDao())
    }
    single<IWordStatsRepository> { WordStatsRepositoryImpl(get<AppDatabase>().getWordStatsDao()) }
    single<IWordListRepository> { WordListRepositoryImpl(get(), get()) }
}