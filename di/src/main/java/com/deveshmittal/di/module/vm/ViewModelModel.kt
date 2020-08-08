package com.deveshmittal.di.module.vm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.deveshmittal.presentation.mvvm.WeatherVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherVM::class)
    internal abstract fun splashViewModel(viewModel: WeatherVM): ViewModel
//

    @Binds
    internal abstract fun bindViewModelFactory(factory: WheatherViewModelFactory): ViewModelProvider.Factory
}