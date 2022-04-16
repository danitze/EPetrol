package com.example.epetrol.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteGasStationsDao {
    @Insert
    suspend fun insert(gasStation: FavouriteGasStation)

    @Delete
    suspend fun delete(gasStation: FavouriteGasStation)

    @Query("SELECT * FROM favourite_gas_station")
    fun getFavouriteStations(): Flow<List<FavouriteGasStation>>

    @Query("SELECT COUNT(*) FROM favourite_gas_station where station_id = :gasStationId")
    suspend fun isGasStationFavourite(gasStationId: String): Int
}