package com.deveshmittal.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteLocaltaion(
        @SerializedName("name") val name: String,
        @SerializedName("country") val country: String,
        @SerializedName("lat") val lat: Double,
        @SerializedName("lon") val lon: Double,
        @SerializedName("localtime") val localtime: String// "2019-02-04 1:15"

)
