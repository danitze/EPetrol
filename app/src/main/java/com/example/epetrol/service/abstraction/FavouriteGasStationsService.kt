package com.example.epetrol.service.abstraction

import com.example.epetrol.data.GasStation
import kotlinx.coroutines.flow.Flow

interface FavouriteGasStationsService {
    val favouriteGasStationsFlow: Flow<List<GasStation>>
    suspend fun addGasStationToFavourites(gasStation: GasStation)
    suspend fun removeGasStationFromFavourites(gasStation: GasStation)
    suspend fun isGasStationFavourite(gasStation: GasStation): Boolean
}