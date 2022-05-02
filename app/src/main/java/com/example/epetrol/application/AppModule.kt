package com.example.epetrol.application

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.example.epetrol.room.FavouriteGasStationsDao
import com.example.epetrol.room.FavouriteGasStationsDb
import com.example.epetrol.services.GasStationInfoService
import com.example.epetrol.services.RegionGasStationsService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Singleton
    @Provides
    fun provideGeoCoder(@ApplicationContext context: Context): Geocoder =
        Geocoder(context, Locale.getDefault())

    @Provides
    fun provideBaseUrl() = "https://e-petrol-api-gateway.herokuapp.com/"

    @Singleton
    @Provides
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    @Singleton
    @Provides
    fun provideRegionGasStationsService(retrofit: Retrofit): RegionGasStationsService = retrofit
        .create(RegionGasStationsService::class.java)

    @Singleton
    @Provides
    fun provideGasStationInfoService(retrofit: Retrofit): GasStationInfoService = retrofit
        .create(GasStationInfoService::class.java)

    @Singleton
    @Provides
    fun provideFavouriteGasStationsDb(
        @ApplicationContext appContext: Context
    ): FavouriteGasStationsDb = Room.databaseBuilder(
        appContext,
        FavouriteGasStationsDb::class.java,
        "favourite_gas_stations_db"
    ).build()

    @Singleton
    @Provides
    fun provideFavouriteGasStationsDao(db: FavouriteGasStationsDb): FavouriteGasStationsDao =
        db.favouriteGasStationsDao()

}