package com.example.epetrol.repo

import com.example.epetrol.data.GasStationMarker
import com.example.epetrol.data.RegionGasStation
import com.example.epetrol.getExceptionMessage
import com.example.epetrol.result.NullableResult
import com.example.epetrol.service.GeoService
import com.example.epetrol.toLatLng
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapRepoImpl @Inject constructor(
    private val geoService: GeoService
) : MapRepo {
    override fun getNearestGasStations(
        regionGasStation: RegionGasStation,
        maxResults: Int,
        coordinates: LatLng,
        radius: Double
    ): NullableResult<List<GasStationMarker>> {
        return try {
            val markers = geoService.getNearestGasStations(
                regionGasStation.gasStationName,
                maxResults,
                coordinates,
                radius
            ).map { address ->
                GasStationMarker(
                    regionGasStation.gasStationName,
                    regionGasStation.gasStationId,
                    address.toLatLng()
                )
            }
            if(markers.isNotEmpty()) {
                NullableResult.Data(markers)
            } else {
                NullableResult.Null()
            }
        } catch (e: Exception) {
            NullableResult.Error(e.getExceptionMessage())
        }
    }
}