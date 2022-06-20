package com.example.epetrol.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_gas_station")
data class GasStation(
    @PrimaryKey @ColumnInfo(name = "station_id") var gasStationId: String,
    @ColumnInfo(name = "station_name") var gasStationName: String
)
