package com.deveshmittal.presentation.ui.mapper

import android.content.res.Resources
import com.deveshmittal.domain.model.Weather
import com.deveshmittal.presentation.mvvm.model.WeatherUi
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


open class UiModelMapper @Inject constructor(val resoruces: Resources) {


    fun mapWeather(it: Weather): WeatherUi =
            WeatherUi(city = it.city,
                    celsius = formateTocelsi(it.celsius),
                    celsiusWithLeter = formateToCelsWitLater(it.celsius),
                    weekDay = getWeekDay(it.date))

    fun getWeekDay(date: Date): CharSequence {
        val sdf = SimpleDateFormat("EEEE")
        return sdf.format(date)
    }

    fun formateTocelsi(ce: Double): CharSequence {
        val DEGREE = "\u00b0";
        return "$ce$DEGREE"
    }

    fun formateToCelsWitLater(ce: Double): CharSequence {
        return "$ce C"
    }

}