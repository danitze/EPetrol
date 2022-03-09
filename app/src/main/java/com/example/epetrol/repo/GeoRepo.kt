package com.example.epetrol.repo

import com.example.epetrol.services.GasStationsInfoService
import javax.inject.Inject

class GeoRepo @Inject constructor(
    private val gasStationsInfoService: GasStationsInfoService
) {
    suspend fun getGasStationsInfo(region: String) = gasStationsInfoService
        .getRegionalStations(region)
}