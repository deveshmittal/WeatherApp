package com.deveshmittal.data.remote.repositry

import com.deveshmittal.data.mapper.RemoteToDomainMapper
import com.deveshmittal.data.remote.api.WeatherApi
import com.deveshmittal.data.remote.client.RestClient
import com.deveshmittal.domain.model.Location
import com.deveshmittal.domain.model.Weather
import com.deveshmittal.domain.repository.WeatherRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(val restClient: RestClient,
                                                val remoteToDomainMapper: RemoteToDomainMapper) : WeatherRepository {


    companion object {
        const val KEY = "c13d66a91f214fef936183723190906"
        const val daysCount = 7
    }

    val api: WeatherApi by lazy {
        restClient.provideWeatherApi()
    }

    override fun provideCurrentLocation(loc: Location): Single<Weather> =
            api.getCurrentWeateher(KEY, reformatRequest(loc))
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map { remoteToDomainMapper.map(it) }

    override fun provideWeatherForecastLocation(loc: Location): Single<List<Weather>> =
            api.getforecast(KEY, reformatRequest(loc), daysCount)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map { result -> remoteToDomainMapper.map(result) }


    fun reformatRequest(latLong: Location) = "${latLong.lat},${latLong.lon}"
}