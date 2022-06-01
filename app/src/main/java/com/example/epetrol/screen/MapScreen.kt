package com.example.epetrol.screen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.epetrol.viewmodel.MapScreenViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(viewModel: MapScreenViewModel = hiltViewModel()) {
    val mapScreenState = viewModel.mapScreenState

    val cameraPositionState = rememberCameraPositionState {
        position =
            CameraPosition.fromLatLngZoom(
                mapScreenState.coordinates,
                16f
            )
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        mapScreenState.markers.forEach { stationMarker ->
            Marker(
                position = stationMarker.coordinates,
                title = stationMarker.stationName,
            )
        }
    }
}