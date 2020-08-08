package com.deveshmittal.di.module

import com.deveshmittal.di.PerApp
import com.deveshmittal.domain.ui.UIErrorMapper
import com.deveshmittal.presentation.ui.mapper.UiErrorMapperImpl
import dagger.Binds
import dagger.Module

@Module
interface UiMappersModule {

    @Binds
    @PerApp
    fun bindUIErrorMapper(uc: UiErrorMapperImpl): UIErrorMapper
}