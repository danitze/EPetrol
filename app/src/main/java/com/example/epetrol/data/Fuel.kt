package com.example.epetrol.data

import com.google.gson.annotations.SerializedName

data class Fuel(
    @SerializedName("fuelType")
    val fuelType: String = "",

    @SerializedName("price")
    val price: Double = 0.toDouble()
)
