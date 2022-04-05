package com.example.epetrol.services

import com.example.epetrol.data.FuelInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FuelInfoService {
    @GET("api/v1/fuel-info")
    suspend fun getFuelInfo(@Query("regionLatin") region: String): Response<List<FuelInfo>>
}