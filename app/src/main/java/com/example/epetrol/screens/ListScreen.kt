package com.example.epetrol.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.epetrol.data.FuelInfo
import com.example.epetrol.formPriceText
import com.example.epetrol.getPainterId
import com.example.epetrol.safeLet
import com.example.epetrol.viewmodels.MainViewModel

@Composable
fun ListScreen(viewModel: MainViewModel = hiltViewModel()) {
    val stationsMapState = viewModel.stationsFlow.collectAsState(mapOf())
    GasStationsCard(stations = stationsMapState.value.toList())
}

@Composable
fun GasStationsCard(stations: List<Pair<String, List<FuelInfo>>>) {
    LazyColumn {
        items(stations) { station ->
            GasStationCard(station = station)
        }
    }
}

@Composable
fun GasStationCard(station: Pair<String, List<FuelInfo>>) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp,
        color = MaterialTheme.colors.surface,
        modifier = Modifier.padding(10.dp)
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(100.dp)
            ) {

                Image(
                    painter = painterResource(id = getPainterId(station.first)),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(100.dp)
                        .border(1.dp, MaterialTheme.colors.primary)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = station.first,
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.subtitle1,
                    fontSize = 40.sp
                )
            }
            station.second.forEach { fuelInfo ->
                FuelTypePricing(fuelInfo = fuelInfo)
            }
        }
    }
}

@Composable
fun FuelTypePricing(fuelInfo: FuelInfo) {
    with(fuelInfo) {
        safeLet(fuelType, price) { type, price ->
            Row {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = type,
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.subtitle2,
                        fontSize = 25.sp
                    )
                }
                Column(modifier = Modifier.weight(4f)) {
                    Text(
                        text = formPriceText(price),
                        fontSize = 30.sp
                    )
                }
            }
        }
    }
}