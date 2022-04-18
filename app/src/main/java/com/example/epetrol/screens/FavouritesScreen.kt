package com.example.epetrol.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.epetrol.getPainterId
import com.example.epetrol.room.GasStation
import com.example.epetrol.viewmodels.MainViewModel

@Composable
fun FavouritesScreen(viewModel: MainViewModel = hiltViewModel()) {
    val favouriteGasStationsState = viewModel.favouriteGasStationsFlow
        .collectAsState(initial = listOf())
    if(favouriteGasStationsState.value.isNotEmpty()) {
        FavouriteGasStationsCard(viewModel = viewModel)
    } else {
        EmptyListCard()
    }
}

@Composable
fun FavouriteGasStationsCard(viewModel: MainViewModel) {
    val favouriteGasStationsState = viewModel.favouriteGasStationsFlow
        .collectAsState(initial = listOf())
    LazyColumn {
        items(favouriteGasStationsState.value) { station ->
            FavouriteGasStationCard(station = station, viewModel = viewModel)
        }
    }
}

@Composable
fun FavouriteGasStationCard(station: GasStation, viewModel: MainViewModel) {

    val favouriteGasStationsState = viewModel.favouriteGasStationsFlow
        .collectAsState(initial = listOf())

    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp,
        color = MaterialTheme.colors.surface,
        modifier = Modifier.padding(5.dp),
        border = BorderStroke(2.dp, MaterialTheme.colors.primaryVariant),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(100.dp)
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = getPainterId(station.gasStationId)),
                contentDescription = "Logo",
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
                    viewModel.changeGasStationFavouriteState(station)
                },
                modifier = Modifier.weight(2f),
            ) {
                Icon(
                    imageVector = if (favouriteGasStationsState.value.contains(station))
                        Icons.Outlined.Favorite
                    else
                        Icons.Outlined.FavoriteBorder,
                    contentDescription = "heart",
                    tint = Color.Red,
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EmptyListCard() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
            .padding(10.dp)
    ) {

        val text = createRef()

        Text(
            text = "Favourites list is empty",
            color = Color.White,
            style = MaterialTheme.typography.h1,
            fontSize = 35.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(text) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
        )
    }
}