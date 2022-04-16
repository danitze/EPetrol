package com.example.epetrol.services

import com.example.epetrol.data.GasStation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GasStationsService {
    @GET("api/v1/fuel-info")
    suspend fun getGasStations(@Query("regionLatin") region: String): Response<List<GasStation>>
}