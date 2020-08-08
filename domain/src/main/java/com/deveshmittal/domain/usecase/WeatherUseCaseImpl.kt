package com.deveshmittal.domain.usecase

import com.deveshmittal.domain.model.Location
import com.deveshmittal.domain.model.Weather
import com.deveshmittal.domain.repository.LocationRepository
import com.deveshmittal.domain.repository.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class WeatherUseCaseImpl @Inject constructor(
        val weatherRep: WeatherRepository,
        val lcoationRe: LocationRepository
) : WeatherUseCase {


    override fun provideCurrentLocation(): Single<Weather> =
            lcoationRe.provideLocation().flatMap { loc: Location ->
                weatherRep.provideCurrentLocation(loc)
            }

    override fun provideWeatherForecastLocation(): Single<List<Weather>> =
            lcoationRe.provideLocation().flatMap { loc: Location ->
                weatherRep.provideWeatherForecastLocation(loc)
            }

}

