package com.example.epetrol.repo

import com.example.epetrol.services.GasStationsService
import javax.inject.Inject

class GeoRepo @Inject constructor(
    private val gasStationsService: GasStationsService
) {
    suspend fun getGasStations(region: String) = gasStationsService
        .getGasStations(region)
}