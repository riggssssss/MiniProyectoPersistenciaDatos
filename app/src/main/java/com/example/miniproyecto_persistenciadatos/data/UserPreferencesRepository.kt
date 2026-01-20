package com.example.miniproyecto_persistenciadatos.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository(private val context: Context) {
    private val FONT_SIZE_KEY = intPreferencesKey("font_size")
    private val DARK_THEME_KEY = booleanPreferencesKey("is_dark_theme")
    private val DEFAULT_MOOD_KEY = stringPreferencesKey("default_mood")
    
    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data
        .map { preferences ->
            UserPreferences(
                fontSize = preferences[FONT_SIZE_KEY] ?: 16,
                isDarkTheme = preferences[DARK_THEME_KEY] ?: false,
                defaultMood = preferences[DEFAULT_MOOD_KEY] ?: "neutral"
            )
        }
    
    suspend fun updateFontSize(fontSize: Int) {
        context.dataStore.edit { preferences ->
            preferences[FONT_SIZE_KEY] = fontSize
        }
    }
    
    suspend fun updateDarkTheme(isDarkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = isDarkTheme
        }
    }
    
    suspend fun updateDefaultMood(mood: String) {
        context.dataStore.edit { preferences ->
            preferences[DEFAULT_MOOD_KEY] = mood
        }
    }
}
