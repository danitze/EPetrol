package com.example.epetrol.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.epetrol.data.GasStation
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteGasStationsDao {
    @Insert
    suspend fun insert(gasStation: GasStation)

    @Delete
    suspend fun delete(gasStation: GasStation)

    @Query("SELECT * FROM favourite_gas_station")
    fun getFavouriteStations(): Flow<List<GasStation>>

    @Query("SELECT COUNT(*) FROM favourite_gas_station where station_id = :gasStationId")
    suspend fun isGasStationFavourite(gasStationId: String): Int
}