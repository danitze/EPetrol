package com.example.epetrol

import com.example.epetrol.data.RegionGasStation
import com.example.epetrol.room.GasStation
import java.lang.Exception

fun formPriceText(price: Double): String = "$price UAH"

fun RegionGasStation.toGasStation(): GasStation = GasStation(
    gasStationId = gasStationId,
    gasStationName = gasStationName
)

fun Exception.getExceptionMessage() = localizedMessage ?: "Unknown error occured"