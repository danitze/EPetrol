package com.example.epetrol.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.epetrol.data.GasStation

@Database(entities = [GasStation::class], version = 1)
abstract class FavouriteGasStationsDb: RoomDatabase() {
    abstract fun favouriteGasStationsDao(): FavouriteGasStationsDao
}