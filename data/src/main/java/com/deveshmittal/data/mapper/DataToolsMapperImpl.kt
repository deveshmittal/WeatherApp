package com.deveshmittal.data.mapper

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.inject.Inject

class DataToolsMapperImpl @Inject constructor() : DataToolsMapper {

    val gson = GsonBuilder()
//                    .registerTypeAdapter()
            .setLenient()
            .create()

    override fun provideGson(): Gson = gson


}
