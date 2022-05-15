package com.example.epetrol.screen

import android.content.Intent
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.epetrol.GAS_STATION_ID_KEY
import com.example.epetrol.R
import com.example.epetrol.activity.GasStationInfoActivity
import com.example.epetrol.room.GasStation
import com.example.epetrol.viewmodel.MainViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun FavouritesScreen(baseUrl: String, viewModel: MainViewModel = hiltViewModel()) {
    val favouriteGasStationsState = viewModel.favouriteGasStationsFlow
        .collectAsState(initial = listOf())
    if(favouriteGasStationsState.value.isNotEmpty()) {
        FavouriteGasStationsCard(
            baseUrl = baseUrl,
            viewModel = viewModel,
            favouriteGasStationsState = favouriteGasStationsState
        )
    } else {
        EmptyListCard()
    }
}

@Composable
fun FavouriteGasStationsCard(
    baseUrl: String,
    viewModel: MainViewModel,
    favouriteGasStationsState: State<List<GasStation>>
) {
    LazyColumn {
        items(items = favouriteGasStationsState.value) { station ->
            FavouriteGasStationCard(
                station = station,
                baseUrl = baseUrl,
                viewModel = viewModel,
                favouriteGasStationsState = favouriteGasStationsState
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavouriteGasStationCard(
    station: GasStation,
    baseUrl: String,
    viewModel: MainViewModel,
    favouriteGasStationsState: State<List<GasStation>>
) {

    val context = LocalContext.current

    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp,
        color = MaterialTheme.colors.surface,
        modifier = Modifier.padding(5.dp),
        border = BorderStroke(2.dp, MaterialTheme.colors.primaryVariant),
        onClick = {
            val intent = Intent(context, GasStationInfoActivity::class.java).apply {
                putExtra(GAS_STATION_ID_KEY, station.gasStationId)
            }
            context.startActivity(intent)
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(100.dp)
                .padding(10.dp)
        ) {
            GlideImage(
                imageModel = "${baseUrl}api/v1/fuel-info/logo?gasStationId=${station.gasStationId}",
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
        modifier = Modifier
            .fillMaxSize()
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