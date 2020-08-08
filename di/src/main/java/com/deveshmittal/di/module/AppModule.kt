package com.deveshmittal.di.module

import com.deveshmittal.domain.callback.IActivityListener
import com.deveshmittal.domain.callback.IApplicationListener
import dagger.Module
import dagger.multibindings.Multibinds

/**
 * Created by Devesh Mittal on 10.06.2019.
 *
 */
@Module
interface AppModule {

    @Multibinds
    fun applicationListeners(): Set<IApplicationListener>

    @Multibinds
    fun activityListeners(): Set<IActivityListener>

}