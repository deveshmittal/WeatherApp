package com.deveshmittal.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteForecastWeather (
        @SerializedName("location") val location: RemoteLocaltaion,
        @SerializedName("current") val weather: RemoteWeatherData,
        @SerializedName("forecast") val forecast: RemoteForecast
        )