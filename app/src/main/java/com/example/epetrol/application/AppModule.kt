package com.example.epetrol.application

import com.example.epetrol.services.GasStationsInfoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //TODO
    @Provides
    fun provideBaseUrl() = "https://example-hilt-retrofit-default-rtdb.firebaseio.com/"

    @Singleton
    @Provides
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    @Singleton
    @Provides
    fun provideGasStationsInfoService(retrofit: Retrofit): GasStationsInfoService = retrofit
        .create(GasStationsInfoService::class.java)

}