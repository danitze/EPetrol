package com.example.epetrol.screens

import androidx.compose.foundation.BorderStroke
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
import com.example.epetrol.data.Fuel
import com.example.epetrol.data.GasStation
import com.example.epetrol.formPriceText
import com.example.epetrol.getPainterId
import com.example.epetrol.safeLet
import com.example.epetrol.viewmodels.MainViewModel

@Composable
fun ListScreen(viewModel: MainViewModel = hiltViewModel()) {
    val gasStationsState = viewModel.gasStationsFlow.collectAsState(listOf())
    GasStationsCard(stations = gasStationsState.value)
}

@Composable
fun GasStationsCard(stations: List<GasStation>) {
    LazyColumn {
        items(stations) { station ->
            GasStationCard(station = station)
        }
    }
}

@Composable
fun GasStationCard(station: GasStation) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp,
        color = MaterialTheme.colors.surface,
        modifier = Modifier.padding(10.dp),
        border = BorderStroke(2.dp, MaterialTheme.colors.primaryVariant),
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(100.dp)
            ) {

                Image(
                    painter = painterResource(id = getPainterId(station.gasStationId)),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(100.dp)
                        .border(1.dp, MaterialTheme.colors.primary)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = station.gasStationName,
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.subtitle1,
                    fontSize = 40.sp
                )
            }
            station.fuelList.forEach { fuel ->
                FuelTypePricing(fuel = fuel)
            }
        }
    }
}

@Composable
fun FuelTypePricing(fuel: Fuel) {
    with(fuel) {
        safeLet(fuelType, price) { type, price ->
            Row {
                Column(
                    modifier = Modifier.weight(2f)
                        .padding(start = 5.dp),
                ) {
                    Text(
                        text = type,
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.subtitle2,
                        fontSize = 30.sp
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(5f)) {
                    Text(
                        text = formPriceText(price),
                        color = MaterialTheme.colors.primaryVariant,
                        fontSize = 25.sp
                    )
                }
            }
        }
    }
}