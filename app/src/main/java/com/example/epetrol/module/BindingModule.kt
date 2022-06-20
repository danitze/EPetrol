package com.example.epetrol.module

import com.example.epetrol.repo.abstraction.AppRepo
import com.example.epetrol.repo.abstraction.AuthRepo
import com.example.epetrol.repo.abstraction.MapRepo
import com.example.epetrol.repo.implementation.AppRepoImpl
import com.example.epetrol.repo.implementation.AuthRepoImpl
import com.example.epetrol.repo.implementation.MapRepoImpl
import com.example.epetrol.service.abstraction.FavouriteGasStationsService
import com.example.epetrol.service.abstraction.GasStationsStorageService
import com.example.epetrol.service.abstraction.GeoService
import com.example.epetrol.service.abstraction.TokenStorageService
import com.example.epetrol.service.implementation.FavouriteGasStationsServiceImpl
import com.example.epetrol.service.implementation.GasStationsStorageServiceImpl
import com.example.epetrol.service.implementation.GeoServiceImpl
import com.example.epetrol.service.implementation.TokenStorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingModule {
    @Binds
    abstract fun bindAppRepo(appRepoImpl: AppRepoImpl): AppRepo

    @Binds
    abstract fun bindAuthRepo(authRepoImpl: AuthRepoImpl): AuthRepo

    @Binds
    abstract fun bindMapRepo(mapRepoImpl: MapRepoImpl): MapRepo

    @Binds
    abstract fun bindGasStationsStorageService(
        gasStationsStorageServiceImpl: GasStationsStorageServiceImpl
    ): GasStationsStorageService

    @Binds
    abstract fun bindGeoService(geoServiceImpl: GeoServiceImpl): GeoService

    @Binds
    abstract fun binFavouriteGasStationsService(
        favouriteGasStationsServiceImpl: FavouriteGasStationsServiceImpl
    ): FavouriteGasStationsService

    @Binds
    abstract fun bindTokensStorageService(
        tokenStorageServiceImpl: TokenStorageServiceImpl
    ): TokenStorageService
}