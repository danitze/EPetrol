package com.example.epetrol

import android.location.Address
import android.location.Location
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.epetrol.data.RegionGasStation
import com.example.epetrol.room.GasStation
import com.google.android.gms.maps.model.LatLng
import java.util.*

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

fun getFormattedDate(): String {
    return "${Calendar.DAY_OF_MONTH}.${Calendar.MONTH}.${Calendar.YEAR}"
}

fun Location.toLatLng(): LatLng = LatLng(
    latitude,
    longitude
)

fun Address.toLatLng(): LatLng = LatLng(
    latitude,
    longitude
)