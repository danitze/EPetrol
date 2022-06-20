package com.example.epetrol.repo.abstraction

import com.example.epetrol.data.GasStationMarker
import com.example.epetrol.data.RegionGasStation
import com.example.epetrol.result.NullableResult
import com.google.android.gms.maps.model.LatLng

interface MapRepo {
    fun getNearestGasStations(
        regionGasStation: RegionGasStation,
        maxResults: Int,
        coordinates: LatLng,
        radius: Double
    ): NullableResult<List<GasStationMarker>>
}