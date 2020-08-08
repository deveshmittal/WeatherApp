package com.deveshmittal.di

import android.util.Log
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins

/**
 * Created by Devesh Mittal on 10.06.2019.
 *
 */
class WeatherApp : DaggerApplication() {


    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? =
            AppInjector.init(this)

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler { throwable ->
            Log.e("WeatherApp", "handled rx exception ${throwable::class.java.simpleName} " +
                    "\n\tmessage: \t${throwable.message}", throwable)
        }

    }

}