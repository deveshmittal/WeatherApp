package com.deveshmittal.data.remote.client

import android.content.Context
import com.deveshmittal.data.mapper.DataToolsMapper
import com.deveshmittal.data.remote.api.WeatherApi
import com.deveshmittal.domain.di.AppContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


open class RestClientImplementation @Inject constructor(
        @AppContext val context: Context,
        val dataTools: DataToolsMapper) : RestClient {


    companion object {
        const val BASE_URL = "https://api.apixu.com/v1/"
        const val TIME_OUT = 25L
    }

    val gson = dataTools.provideGson()
    var okHttpClient = provideOkHttp()
    var retrofit: Retrofit = cretaeRetrofit(okHttpClient)


    fun provideOkHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)

        builder.addInterceptor(LogInterceptor())
        return builder.build()
    }


    fun cretaeRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


    override fun provideWeatherApi() = retrofit.create(WeatherApi::class.java)!!

}