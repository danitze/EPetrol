package com.example.epetrol.services

import com.example.epetrol.room.FavouriteGasStation
import com.example.epetrol.room.FavouriteGasStationsDao
import javax.inject.Inject

class RoomService @Inject constructor(
    private val favouriteGasStationsDao: FavouriteGasStationsDao
) {
    val favouriteGasStationsFlow = favouriteGasStationsDao.getFavouriteStations()

    suspend fun addGasStationToFavourites(gasStation: FavouriteGasStation) = favouriteGasStationsDao
        .insert(gasStation)

    suspend fun removeGasStationFromFavourites(gasStation: FavouriteGasStation) =
        favouriteGasStationsDao.delete(gasStation)

    suspend fun isGasStationFavourite(gasStation: FavouriteGasStation): Boolean =
        favouriteGasStationsDao.isGasStationFavourite(gasStation.gasStationId) > 0
}