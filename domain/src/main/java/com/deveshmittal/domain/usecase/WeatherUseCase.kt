package com.deveshmittal.domain.usecase

import com.deveshmittal.domain.model.Weather
import io.reactivex.Single

interface WeatherUseCase {

    fun provideCurrentLocation(): Single<Weather>
    fun provideWeatherForecastLocation(): Single<List<Weather>>
}