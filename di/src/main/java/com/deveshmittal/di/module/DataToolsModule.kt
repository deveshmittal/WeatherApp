package com.deveshmittal.di.module

import com.deveshmittal.data.mapper.DataToolsMapper
import com.deveshmittal.data.mapper.DataToolsMapperImpl
import com.deveshmittal.di.PerApp
import dagger.Binds
import dagger.Module

@Module
interface DataToolsModule {

    @Binds
    @PerApp
    fun bindDataToolsModulee(uc: DataToolsMapperImpl): DataToolsMapper
}