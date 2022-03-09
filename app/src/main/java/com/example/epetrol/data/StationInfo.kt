package com.example.epetrol.data

import com.google.gson.annotations.SerializedName

data class StationInfo(
    @SerializedName("region")
    val region: String? = null,
    @SerializedName("fuelType")
    val fuelType: String? = null,
    @SerializedName("gasStation")
    val gasStation: String? = null,
    @SerializedName("price")
    val price: Double = 0.toDouble()
)
