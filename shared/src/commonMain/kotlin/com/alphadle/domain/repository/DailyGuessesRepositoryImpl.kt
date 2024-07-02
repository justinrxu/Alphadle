package com.alphadle.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.alphadle.data.entity.DailyGuesses
import com.alphadle.domain.repository.interfaces.IDailyGuessesRepository
import com.alphadle.domain.util.currentGameDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class DailyGuessesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : IDailyGuessesRepository {
    private val guessesKey: Preferences.Key<String> = stringPreferencesKey("guesses")

    override fun getDailyGuesses(): Flow<DailyGuesses> {
        val answer = getDailyWord()
        val date = currentGameDate()

        return dataStore.data.map { preferences ->
            DailyGuesses(
                guesses = preferences[guessesKey]?.let { Json.decodeFromString(it) } ?: emptyList(),
                answer = answer,
                date = date
            )
        }
    }

    override suspend fun updateDailyGuesses(dailyGuesses: DailyGuesses) {
        dataStore.edit { settings ->
            settings[guessesKey] = Json.encodeToString(dailyGuesses.guesses)
        }
    }

    override suspend fun deleteDailyGuesses() {
        dataStore.edit { settings ->
            settings.minusAssign(guessesKey)
        }
    }

    private fun getDailyWord(): String = "YIPPEE"
}