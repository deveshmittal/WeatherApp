package com.deveshmittal.di.module

import com.deveshmittal.di.PerApp
import com.deveshmittal.domain.usecase.WeatherUseCase
import com.deveshmittal.domain.usecase.WeatherUseCaseImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Devesh Mittal on 10.06.2019.
 *
 */
@Module
interface UseCaseModule {


    @Binds
    @PerApp
    fun bindWheterUC(uc: WeatherUseCaseImpl): WeatherUseCase
}