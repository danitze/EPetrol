package com.example.epetrol.module

import com.example.epetrol.repo.AppRepo
import com.example.epetrol.repo.AppRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingModule {
    @Binds
    abstract fun bindAppRepo(appRepoImpl: AppRepoImpl): AppRepo
}