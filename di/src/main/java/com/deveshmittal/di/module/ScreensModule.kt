package com.deveshmittal.di.module

import com.deveshmittal.di.PerActivity
import com.deveshmittal.presentation.ui.screens.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ScreensModule {

    @PerActivity
    @ContributesAndroidInjector()
    fun mainActivity(): MainActivity


}