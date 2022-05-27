package com.example.epetrol.state

import com.example.epetrol.data.RegionGasStation

data class ListScreenState(
    val isLoading: Boolean = false,
    val data: List<RegionGasStation> = listOf(),
    val error: String? = null
)
