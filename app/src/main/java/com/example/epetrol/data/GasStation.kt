package com.example.epetrol.data

import com.google.gson.annotations.SerializedName

data class GasStation(
    @SerializedName("gasStationId")
    val gasStationId: String = "",

    @SerializedName("gasStationName")
    val gasStationName: String = "",

    @SerializedName("region")
    val region: String = "",

    @SerializedName("fuelPrices")
    val fuelList: List<Fuel> = listOf()
)
