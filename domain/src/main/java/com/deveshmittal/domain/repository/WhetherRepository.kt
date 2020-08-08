package com.deveshmittal.domain.repository

import com.deveshmittal.domain.model.Location
import com.deveshmittal.domain.model.Weather
import io.reactivex.Single

interface WeatherRepository {
    fun provideCurrentLocation(loc: Location): Single<Weather>
    fun provideWeatherForecastLocation(loc: Location): Single<List<Weather>>
}