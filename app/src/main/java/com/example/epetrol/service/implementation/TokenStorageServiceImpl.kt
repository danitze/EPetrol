package com.example.epetrol.service.implementation

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.epetrol.TOKEN_DATASTORE
import com.example.epetrol.service.abstraction.TokenStorageService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenStorageServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
): TokenStorageService {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = TOKEN_DATASTORE
    )

    override val tokensFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[TOKEN_KEY] ?: ""
    }

    override suspend fun saveToken(userToken: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = userToken
        }
    }

    override suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }

    override suspend fun getToken(): String = tokensFlow.first()

    companion object {
        val TOKEN_KEY = stringPreferencesKey(name = "user_token")
    }
}