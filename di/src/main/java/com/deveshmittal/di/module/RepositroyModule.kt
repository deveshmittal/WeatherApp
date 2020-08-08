package com.deveshmittal.di.module

import com.deveshmittal.data.remote.repositry.LocationRepositoryImpl
import com.deveshmittal.data.remote.repositry.WeatherRepositoryImpl
import com.deveshmittal.di.PerApp
import com.deveshmittal.domain.repository.LocationRepository
import com.deveshmittal.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositroyModule {
    @Binds
    @PerApp
    fun bindWeatherRepository(uc: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @PerApp
    fun bindLocationRepository(uc: LocationRepositoryImpl): LocationRepository
}