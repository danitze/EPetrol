package com.example.epetrol.intent

import com.example.epetrol.room.GasStation

sealed class MainIntent {
    data class ChangeGasStationFavouriteState(val gasStation: GasStation) : MainIntent()
    object SignOut : MainIntent()
}
