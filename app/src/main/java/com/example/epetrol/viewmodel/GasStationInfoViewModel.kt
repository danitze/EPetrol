package com.example.epetrol.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.epetrol.GAS_STATION_ID_KEY
import com.example.epetrol.data.GasStationInfo
import com.example.epetrol.repo.AppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GasStationInfoViewModel @Inject constructor(
    private val appRepo: AppRepo,
    private val savedStateHandle: SavedStateHandle
    ): ViewModel() {

    private val _gasStationInfoFlow = MutableStateFlow(GasStationInfo())

    val gasStationInfoFlow = _gasStationInfoFlow.asStateFlow()

    init {
        getGasStationInfo()
    }

    private fun getGasStationInfo() {

        val gasStationId = savedStateHandle.get<String>(GAS_STATION_ID_KEY) ?: return

        viewModelScope.launch {
            _gasStationInfoFlow.value = appRepo.getGasStationInfo(gasStationId).body()
                ?: GasStationInfo()
        }

        gasStationInfoFlow.onEach {
            Log.d("MyTag", it.gasStationName)
        }.launchIn(viewModelScope)
    }

}