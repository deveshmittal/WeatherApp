package com.deveshmittal.di.component

import android.content.Context
import android.content.res.Resources
import com.deveshmittal.di.PerApp
import com.deveshmittal.di.WeatherApp
import com.deveshmittal.di.module.*
import com.deveshmittal.di.module.vm.ViewModelModule
import com.deveshmittal.domain.callback.IApplicationListener
import com.deveshmittal.domain.di.AppContext
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule


@PerApp
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ScreensModule::class,
    ViewModelModule::class,
    UiMappersModule::class,
    DataToolsModule::class,
    RepositroyModule::class,
    RemoteModule::class,
    UseCaseModule::class
])
interface AppComponent : AndroidInjector<WeatherApp> {

    fun applicationListeners(): @JvmSuppressWildcards Set<IApplicationListener>

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(@AppContext context: Context): Builder

        @BindsInstance
        fun applicationResources(res: Resources): Builder

        fun build(): AppComponent
    }
}