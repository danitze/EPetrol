package com.example.epetrol.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.epetrol.R
import com.example.epetrol.activity.ui.theme.EPetrolTheme
import com.example.epetrol.data.Fuel
import com.example.epetrol.data.GasStationInfo
import com.example.epetrol.formPriceText
import com.example.epetrol.viewmodel.GasStationInfoViewModel
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GasStationInfoActivity : ComponentActivity() {

    @Inject lateinit var baseUrl: String

    private val viewModel: GasStationInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
        setContent {
            EPetrolTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    val gasStationState = viewModel.gasStationInfoFlow.collectAsState()
                    if(gasStationState.value == GasStationInfo()) {

                    } else {
                        GasStationCard(gasStationInfo = gasStationState.value)
                    }
                }
            }
        }
    }

    @Composable
    fun GasStationCard(gasStationInfo: GasStationInfo) {

        val context = LocalContext.current

        Column(modifier = Modifier.padding(20.dp)) {
            GlideImage(
                imageModel = "${baseUrl}api/v1/fuel-info/logo?gasStationId=${gasStationInfo.gasStationId}",
                contentDescription = "Logo",
                contentScale = ContentScale.FillHeight,
                placeHolder = ImageVector.vectorResource(id = R.drawable.ic_placeholder),
                modifier = Modifier.height(300.dp),
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = gasStationInfo.gasStationName,
                color = Color.White,
                style = MaterialTheme.typography.h1,
                fontSize = 40.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            if(gasStationInfo.email != "null" || gasStationInfo.phoneNumber != "null") {

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Contact information",
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle1,
                    fontSize = 30.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        if(gasStationInfo.email?.let { it != "null" } == true) {
                            Text(
                                "email: ",
                                color = Color.White,
                                fontSize = 20.sp,
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        if(gasStationInfo.phoneNumber?.let { it != "null" } == true) {
                            Text(
                                "tel.: ",
                                color = Color.White,
                                fontSize = 20.sp,
                            )
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        if(gasStationInfo.email?.let { it != "null" } == true) {
                            Text(
                                gasStationInfo.email,
                                color = MaterialTheme.colors.secondary,
                                fontSize = 20.sp,
                                modifier = Modifier.clickable {
                                    val intent = Intent(
                                        Intent.ACTION_SENDTO,
                                        Uri.fromParts(
                                            "mailto",
                                            gasStationInfo.email,
                                            null
                                        )
                                    )
                                    context.startActivity(intent)
                                },
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        if(gasStationInfo.phoneNumber?.let { it != "null" } == true) {
                            Text(
                                text = gasStationInfo.phoneNumber,
                                color = MaterialTheme.colors.secondary,
                                fontSize = 20.sp,
                                modifier = Modifier.clickable {
                                    val intent = Intent(
                                        Intent.ACTION_DIAL,
                                        Uri.fromParts(
                                            "tel",
                                            gasStationInfo.phoneNumber,
                                            null
                                        )
                                    )
                                    context.startActivity(intent)
                                },
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Average fuel prices",
                color = Color.White,
                style = MaterialTheme.typography.subtitle1,
                fontSize = 30.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            LazyColumn {
                items(
                    gasStationInfo.averageFuelPrices.filter { it.price != 0.toDouble() }
                ) { fuel ->
                    FuelTypePricing(fuel = fuel)
                }
            }
        }
    }

    @Composable
    fun FuelTypePricing(fuel: Fuel) {
        Spacer(modifier = Modifier.height(10.dp))
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
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }

}