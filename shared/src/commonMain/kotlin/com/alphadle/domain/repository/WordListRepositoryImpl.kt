package com.alphadle.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.alphadle.domain.model.GameData
import com.alphadle.domain.repository.interfaces.IWordListRepository
import com.alphadle.domain.util.currentGameDate
import com.alphadle.domain.util.wordsEpochDate
import com.alphadle.domain.util.normalWordList
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.minus
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

internal class WordListRepositoryImpl(
    private val httpClient: HttpClient,
    private val dataStore: DataStore<Preferences>
): IWordListRepository {
    private companion object {
        const val DAILY_WORDS_KEY = "daily_words"

        const val WORDS_API_URI = "https://api.dictionaryapi.dev/api/v2/entries/en/"
    }

    override suspend fun getDailyWords(): Map<GameData.Difficulty, String> {
        val savedDailyWords = dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(DAILY_WORDS_KEY)]?.let { dailyWordsString ->
                Json.decodeFromString<List<String>>(dailyWordsString)
            }
        }.first()

        val wordList = savedDailyWords ?: retrieveDailyWords().also { wordList ->
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey(DAILY_WORDS_KEY)] =
                    Json.encodeToString(wordList)
            }
        }

        return GameData.Difficulty.entries.mapNotNull { difficulty ->
            wordList.find { word ->
                word.length == difficulty.wordLength
            }?.let { word ->
                difficulty to word
            }
        }.toMap()
    }

    override suspend fun isInWordList(guess: String): Boolean {
        val response: HttpResponse = httpClient.get("$WORDS_API_URI$guess")
        val jsonObject = try {
            Json.parseToJsonElement(response.bodyAsText()).jsonArray[0].jsonObject
        } catch (e: Exception) {
            null
        }

        if (response.status.value !in 200..299 && jsonObject?.containsKey("title") == false) {
            throw Exception(response.bodyAsText())
        }

        return jsonObject?.containsKey("word") == true
    }

    private fun retrieveDailyWords(): List<String> {
        val offset = currentGameDate().minus(wordsEpochDate).days
        return listOf(normalWordList[offset])
    }
}