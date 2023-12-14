package com.example.homework17.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.homework17.App
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object PreferencesRepository{

    val EMAIL = stringPreferencesKey("email")
    val TOKEN = stringPreferencesKey("key")

    suspend fun writeEmailAndToken(email: String, token: String) {
        App.application.applicationContext.dataStore.edit { settings ->
            settings[EMAIL] = email
            settings[TOKEN] = token
        }
    }

    fun readEmail(): Flow<String> {
        return App.application.applicationContext.dataStore.data
            .map { preferences ->
                preferences[EMAIL] ?: ""
            }
    }

    fun readToken() : Flow<String>{
        return App.application.applicationContext.dataStore.data
            .map { preferences ->
                preferences[TOKEN] ?: ""
            }
    }

    suspend fun clearSession() {
        App.application.applicationContext.dataStore.edit { settings ->
            settings.remove(EMAIL)
            settings.remove(TOKEN)
        }
    }
}