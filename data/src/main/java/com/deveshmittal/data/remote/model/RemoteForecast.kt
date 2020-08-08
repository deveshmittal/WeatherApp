package com.deveshmittal.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteForecast(
        @SerializedName("forecastday") val forecastDay: List<ForecustDay>)
