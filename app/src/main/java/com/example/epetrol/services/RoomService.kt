package com.example.epetrol.services

import com.example.epetrol.room.GasStation
import com.example.epetrol.room.FavouriteGasStationsDao
import javax.inject.Inject

class RoomService @Inject constructor(
    private val favouriteGasStationsDao: FavouriteGasStationsDao
) {
    val favouriteGasStationsFlow = favouriteGasStationsDao.getFavouriteStations()

    suspend fun addGasStationToFavourites(gasStation: GasStation) = favouriteGasStationsDao
        .insert(gasStation)

    suspend fun removeGasStationFromFavourites(gasStation: GasStation) =
        favouriteGasStationsDao.delete(gasStation)

    suspend fun isGasStationFavourite(gasStation: GasStation): Boolean =
        favouriteGasStationsDao.isGasStationFavourite(gasStation.gasStationId) > 0
}