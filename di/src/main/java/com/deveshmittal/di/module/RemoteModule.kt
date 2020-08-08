package com.deveshmittal.di.module

import com.deveshmittal.data.remote.client.RestClient
import com.deveshmittal.data.remote.client.RestClientImplementation
import com.deveshmittal.di.PerApp
import dagger.Binds
import dagger.Module

/**
 * Created by Devesh Mittal on 10.06.2019.
 *
 */
@Module
interface RemoteModule {


    @Binds
    @PerApp
    fun provideRestClient(client: RestClientImplementation): RestClient
}