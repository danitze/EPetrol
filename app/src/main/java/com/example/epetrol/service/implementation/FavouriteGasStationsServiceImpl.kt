package com.example.epetrol.service.implementation

import com.example.epetrol.data.GasStation
import com.example.epetrol.room.FavouriteGasStationsDao
import com.example.epetrol.service.abstraction.FavouriteGasStationsService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteGasStationsServiceImpl @Inject constructor(
    private val favouriteGasStationsDao: FavouriteGasStationsDao
): FavouriteGasStationsService {
    override val favouriteGasStationsFlow = favouriteGasStationsDao.getFavouriteStations()

    override suspend fun addGasStationToFavourites(gasStation: GasStation) = favouriteGasStationsDao
        .insert(gasStation)

    override suspend fun removeGasStationFromFavourites(gasStation: GasStation) =
        favouriteGasStationsDao.delete(gasStation)

    override suspend fun isGasStationFavourite(gasStation: GasStation): Boolean =
        favouriteGasStationsDao.isGasStationFavourite(gasStation.gasStationId) > 0
}