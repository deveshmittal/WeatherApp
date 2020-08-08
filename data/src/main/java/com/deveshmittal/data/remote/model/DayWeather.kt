package com.deveshmittal.data.remote.model

import com.google.gson.annotations.SerializedName

data class DayWeather(
        @SerializedName("avgtemp_c") val avgtemp_c: Double
        )