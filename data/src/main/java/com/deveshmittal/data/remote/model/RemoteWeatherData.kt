package com.deveshmittal.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteWeatherData(
        @SerializedName("last_updated") val lastUpdate: String,
        @SerializedName("temp_c") val tempC: Double
)