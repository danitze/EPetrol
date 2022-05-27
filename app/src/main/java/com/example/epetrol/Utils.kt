package com.example.epetrol

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.epetrol.data.RegionGasStation
import com.example.epetrol.room.GasStation

fun formPriceText(price: Double): String = "$price UAH"

fun RegionGasStation.toGasStation(): GasStation = GasStation(
    gasStationId = gasStationId,
    gasStationName = gasStationName
)

fun Exception.getExceptionMessage() = localizedMessage ?: "Unknown error occured"

fun createTokenHeader(token: String) = "Bearer $token"

fun provideGlideUrl(url: String, token: String) = GlideUrl(
    url,
    LazyHeaders.Builder().addHeader("Authorization", token).build()
)