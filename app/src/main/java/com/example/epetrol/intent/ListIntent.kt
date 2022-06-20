package com.example.epetrol.intent

import com.example.epetrol.data.GasStation

sealed class ListIntent {
    data class ChangeGasStationFavouriteState(val gasStation: GasStation) : ListIntent()
}
