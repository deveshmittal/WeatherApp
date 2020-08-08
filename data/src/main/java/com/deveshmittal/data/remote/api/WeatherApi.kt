package com.deveshmittal.data.remote.api

import com.deveshmittal.data.remote.model.RemoteCurrentWeather
import com.deveshmittal.data.remote.model.RemoteForecastWeather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {


    @GET("current.json")
    fun getCurrentWeateher(@Query("key") key: String,
                           @Query("q") latLang: String
    ): Single<RemoteCurrentWeather>


    @GET("forecast.json")
    fun getforecast(
            @Query("key") key: String,
            @Query("q") latLang: String,
            @Query("days") days: Int
    ): Single<RemoteForecastWeather>

}