package com.alphadle.domain.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.alphadle.data.database.AppDatabase
import com.alphadle.data.util.createDataStore
import com.alphadle.data.util.getAppDatabaseBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initApp() {
    val databaseModule = module {
        single<AppDatabase> {
            getAppDatabaseBuilder()
                .setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .build()
//                .apply { runBlocking { getDailyGuessesDao().deleteAllDailyGuesses() } }
        }
    }

    val dataStoreModule = module {
        single<DataStore<Preferences>> {
            createDataStore()
//                .apply { runBlocking { edit { it.clear() } } }
        }
    }

    startKoin {
        modules(
            listOf(
                httpClientModule,
                viewModelsModule, useCasesViewModel, repositoriesModule,
                databaseModule, dataStoreModule
            )
        )
    }
}