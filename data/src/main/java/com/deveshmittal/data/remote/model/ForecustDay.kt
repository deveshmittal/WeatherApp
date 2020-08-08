package com.deveshmittal.data.remote.model

import com.google.gson.annotations.SerializedName

data class ForecustDay (
        @SerializedName("date")val date:String,
        @SerializedName("day")val day:DayWeather
        )