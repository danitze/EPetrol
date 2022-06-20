package com.example.epetrol.service.abstraction

import com.example.epetrol.data.RegionGasStation

interface GasStationsStorageService {
    suspend fun saveRegionGasStations(key: String, regionGasStations: List<RegionGasStation>)
    suspend fun getRegionGasStations(key: String): List<RegionGasStation>
    suspend fun saveLastUpdateDate()
    suspend fun getLastUpdateDate(): String
    suspend fun clearPrefs()
}