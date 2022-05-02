package com.example.epetrol.data

import com.google.gson.annotations.SerializedName

data class GasStationInfo(
    @SerializedName("gasStationId")
    val gasStationId: String = "",

    @SerializedName("gasStationName")
    val gasStationName: String = "",

    @SerializedName("email")
    val email: String? = "",

    @SerializedName("phoneNumber")
    val phoneNumber: String? = "",

    @SerializedName("averagePriceList")
    val averageFuelPrices: List<Fuel> = listOf()
)
