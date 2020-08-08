package com.deveshmittal.data.mapper

import com.deveshmittal.data.remote.model.RemoteCurrentWeather
import com.deveshmittal.data.remote.model.RemoteForecastWeather
import com.deveshmittal.domain.model.Weather
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RemoteToDomainMapper @Inject constructor() {

    private val dateTomeFormatter = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Synchronized
    fun parseDateTime(date: String) = dateTomeFormatter.parse(date)

    @Synchronized
    fun parseDate(date: String) = dateFormatter.parse(date)

    fun map(it: RemoteCurrentWeather): Weather {
        return Weather(it.location.name,
                it.weather.tempC,
                parseDateTime(it.weather.lastUpdate))
    }

    fun map(forecust: RemoteForecastWeather): List<Weather> {
        val name = forecust.location.name;
        return forecust.forecast.forecastDay.map { wd ->
            Weather(name, wd.day.avgtemp_c, parseDate(wd.date))
        }
    }
}