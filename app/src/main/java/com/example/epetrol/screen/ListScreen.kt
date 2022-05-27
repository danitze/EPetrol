package com.example.epetrol.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.epetrol.*
import com.example.epetrol.R
import com.example.epetrol.data.Fuel
import com.example.epetrol.data.RegionGasStation
import com.example.epetrol.intent.ListIntent
import com.example.epetrol.viewmodel.ListScreenViewModel
import com.example.epetrol.viewmodel.MainViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ListScreen(
    baseUrl: String,
    mainViewModel: MainViewModel,
    viewModel: ListScreenViewModel = hiltViewModel()
) {
    val listScreenState = viewModel.listScreenState
    if (listScreenState.data.isNotEmpty()) {
        GasStationsCard(
            stations = listScreenState.data,
            mainViewModel = mainViewModel,
            baseUrl = baseUrl,
            viewModel = viewModel
        )
    }
}

@Composable
fun GasStationsCard(
    stations: List<RegionGasStation>,
    baseUrl: String,
    mainViewModel: MainViewModel,
    viewModel: ListScreenViewModel
) {
    LazyColumn {
        items(stations) { station ->
            GasStationCard(
                station = station,
                mainViewModel = mainViewModel,
                baseUrl = baseUrl,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun GasStationCard(
    station: RegionGasStation,
    mainViewModel: MainViewModel,
    baseUrl: String,
    viewModel: ListScreenViewModel
) {

    val favouriteGasStationsState = viewModel
        .favouriteGasStationsFlow.collectAsState(initial = listOf())

    val tokenState = mainViewModel.tokensFlow.collectAsState(initial = "")

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
                GlideImage(
                    imageModel = provideGlideUrl(
                        url = "${baseUrl}api/v1/fuel-info/logo?gasStationId=${station.gasStationId}",
                        token = createTokenHeader(tokenState.value)
                    ),
                    contentDescription = "Logo",
                    contentScale = ContentScale.FillWidth,
                    placeHolder = ImageVector.vectorResource(id = R.drawable.ic_placeholder),
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colors.primary)
                        .weight(3f),
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = station.gasStationName,
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.subtitle1,
                    fontSize = 30.sp,
                    modifier = Modifier.weight(6f),
                )

                Spacer(modifier = Modifier.width(10.dp))

                IconButton(
                    onClick = {
                        viewModel.onIntent(
                            ListIntent.ChangeGasStationFavouriteState(station.toGasStation())
                        )
                    },
                    modifier = Modifier.weight(2f),
                ) {
                    Icon(
                        imageVector = if (favouriteGasStationsState.value.contains(
                                station.toGasStation()
                            )
                        )
                            Icons.Outlined.Favorite
                        else
                            Icons.Outlined.FavoriteBorder,
                        contentDescription = "heart",
                        tint = Color.Red,
                    )
                }
            }
            station.fuelList.filter { it.price != 0.toDouble() }.forEach { fuel ->
                FuelTypePricing(fuel = fuel)
            }
        }
    }
}

@Composable
fun FuelTypePricing(fuel: Fuel) {
    with(fuel) {
        Row {
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 5.dp),
            ) {
                Text(
                    text = fuelType,
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.subtitle2,
                    fontSize = 25.sp
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(5f)) {
                Text(
                    text = formPriceText(price),
                    color = MaterialTheme.colors.primaryVariant,
                    fontSize = 20.sp
                )
            }
        }
    }
}