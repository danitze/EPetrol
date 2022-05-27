package com.example.epetrol.intent

import com.example.epetrol.room.GasStation

sealed class FavouritesIntent {
    data class ChangeGasStationFavouriteState(val gasStation: GasStation) : FavouritesIntent()
}
