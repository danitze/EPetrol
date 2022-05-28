package com.example.epetrol.service

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.epetrol.REGION_GAS_STATIONS_DATASTORE
import com.example.epetrol.data.RegionGasStation
import com.example.epetrol.getFormattedDate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GasStationsStorageService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = REGION_GAS_STATIONS_DATASTORE
    )

    suspend fun saveRegionGasStations(key: String, regionGasStations: List<RegionGasStation>) {
        context.dataStore.edit { prefs ->
            prefs[stringPreferencesKey(key)] = gson.toJson(regionGasStations)
        }
        saveKey(key)
    }

    suspend fun getRegionGasStations(key: String): List<RegionGasStation> =
        context.dataStore.data.map { prefs ->
            val jsonString =
                prefs[stringPreferencesKey(key)] ?: return@map listOf<RegionGasStation>()
            gson.fromJson(jsonString, object : TypeToken<List<RegionGasStation>>() {}.type)
        }.firstOrNull() ?: listOf()

    suspend fun saveLastUpdateDate() {
        val date = getFormattedDate()
        context.dataStore.edit { prefs ->
            prefs[LAST_UPDATED_KEY] = date
        }
    }

    suspend fun getLastUpdateDate(): String = context.dataStore.data.map { prefs ->
        prefs[LAST_UPDATED_KEY] ?: ""
    }.firstOrNull() ?: ""

    suspend fun clearPrefs() {
        context.dataStore.edit { prefs ->
            getRegionsKeys().forEach { regionKey ->
                prefs.remove(stringPreferencesKey(regionKey))
            }
            prefs.remove(LAST_UPDATED_KEY)
            prefs.remove(KEYS_STORAGE_KEY)
        }
    }

    private suspend fun saveKey(key: String) {
        val keysList = getRegionsKeys().toMutableList()
        keysList.add(key)
        context.dataStore.edit { prefs ->
            prefs[KEYS_STORAGE_KEY] = gson.toJson(keysList)
        }
    }

    private suspend fun getRegionsKeys(): List<String> = context.dataStore.data.map { prefs ->
        val jsonString = prefs[KEYS_STORAGE_KEY] ?: return@map listOf<String>()
        gson.fromJson(jsonString, object : TypeToken<List<String>>() {}.type)
    }.firstOrNull() ?: listOf()

    companion object {
        val LAST_UPDATED_KEY = stringPreferencesKey("last_updated")
        val KEYS_STORAGE_KEY = stringPreferencesKey("keys_storage")
    }
}