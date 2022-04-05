package com.example.epetrol.repo

import com.example.epetrol.services.FuelInfoService
import javax.inject.Inject

class GeoRepo @Inject constructor(
    private val gasStationsInfoService: FuelInfoService
) {
    suspend fun getFuelInfo(region: String) = gasStationsInfoService
        .getFuelInfo(region)
}