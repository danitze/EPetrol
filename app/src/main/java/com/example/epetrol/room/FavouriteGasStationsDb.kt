package com.example.epetrol.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavouriteGasStation::class], version = 1)
abstract class FavouriteGasStationsDb: RoomDatabase() {
    abstract fun favouriteGasStationsDao(): FavouriteGasStationsDao
}