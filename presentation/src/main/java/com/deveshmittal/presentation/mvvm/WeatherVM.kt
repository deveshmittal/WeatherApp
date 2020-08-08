package com.deveshmittal.presentation.mvvm

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.deveshmittal.domain.exception.LocationPermisionDenied
import com.deveshmittal.domain.ui.UIErrorMapper
import com.deveshmittal.domain.usecase.WeatherUseCase
import com.deveshmittal.presentation.mvvm.model.*
import com.deveshmittal.presentation.ui.mapper.UiModelMapper
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

open class WeatherVM @Inject constructor(
        val weatherUseCase: WeatherUseCase,
        val modelUiMapper: UiModelMapper,
        val errorMapper: UIErrorMapper
) : BaseVM() {

    /**
     * to prevent request permission and data loaded on every configChange
     */
    var ifDataWasReguested: Boolean = false


    val lvScreenState = MutableLiveData<ScreenState>()
    val lvCurrentWeather = MutableLiveData<WeatherUi>()

    val lvWeatherForecast = MutableLiveData<List<WeatherUi>>()
    val lvWeatherForecastVisibility = Transformations.map(lvWeatherForecast)
    { list -> !list.isEmpty() }

    val lvErrorText = Transformations.map(lvScreenState) { sc ->
        if (sc is ErrorState) {
            sc.message
        } else ""
    }

    fun requestLocationPermission(activity: Activity) {
        if (!ifDataWasReguested)
            RxPermissions(activity)
                    .request(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe { grunted ->
                        if (grunted)
                            updateWeather()
                        else postErrorState(LocationPermisionDenied()) { requestLocationPermission(activity) }
                    }.addTo(compositeDisposable)
    }

    fun updateWeather() {
        ifDataWasReguested = true
        getCurrentWeather(true)
    }

    fun getCurrentWeather(autoRequestForecust: Boolean = false) {
        weatherUseCase.provideCurrentLocation()
                .map { wether -> modelUiMapper.mapWeather(wether) }
                .doOnSubscribe { lvScreenState.postValue(Progress()) }
                .doAfterSuccess { if (autoRequestForecust) getWeatherForecast() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ value ->
                    lvScreenState.postValue(Loaded())
                    lvCurrentWeather.postValue(value)
                }, { t: Throwable -> postErrorState(t) { this.getCurrentWeather(autoRequestForecust) } })
                .addTo(compositeDisposable)
    }

    fun getWeatherForecast() {
        weatherUseCase.provideWeatherForecastLocation()
                .map { it.map { wether -> modelUiMapper.mapWeather(wether) } }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ value ->
                    lvWeatherForecast.postValue(value)
                }, { t: Throwable -> postErrorState(t, this::getWeatherForecast) })
                .addTo(compositeDisposable)
    }

    fun retry() {
        lvScreenState.value?.let { state ->
            if (state is ErrorState)
                state.retry.invoke()
        }
    }

    private fun postErrorState(t: Throwable, retry: () -> Unit) {
        lvScreenState.postValue(ErrorState(t, errorMapper.mapErrorToText(t), retry))
    }
}



