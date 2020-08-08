package com.deveshmittal.data.remote.client

import com.deveshmittal.data.remote.api.WeatherApi

/**
 * Created by Devesh Mittal on 10.06.2019.
 *
 */
interface RestClient {
    fun provideWeatherApi(): WeatherApi
}