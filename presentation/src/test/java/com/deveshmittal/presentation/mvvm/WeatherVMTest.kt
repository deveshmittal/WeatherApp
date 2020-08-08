package com.deveshmittal.presentation.mvvm

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.deveshmittal.domain.model.Weather
import com.deveshmittal.domain.ui.UIErrorMapper
import com.deveshmittal.domain.usecase.WeatherUseCase
import com.deveshmittal.presentation.mvvm.model.ErrorState
import com.deveshmittal.presentation.mvvm.model.Loaded
import com.deveshmittal.presentation.mvvm.model.Progress
import com.deveshmittal.presentation.tools.RxSchedulerRule
import com.deveshmittal.presentation.tools.testObserver
import com.deveshmittal.presentation.ui.mapper.UiModelMapper
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.sql.Date


class WeatherVMTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @InjectMocks
    lateinit var classUnderTest: WeatherVM

    @Mock
    lateinit var weatherUseCase: WeatherUseCase
    @Mock
    lateinit var errorMapper: UIErrorMapper
    @Mock
    lateinit var modelMapper: UiModelMapper


    @Test
    fun `init weatherUseCase`() {
        val weatherForecast = classUnderTest.lvWeatherForecast.testObserver()
        val currentWeather = classUnderTest.lvCurrentWeather.testObserver()
        val lvProgress = classUnderTest.lvScreenState.testObserver()
        Truth.assert_().that(weatherForecast.observedValues).isEmpty()
        Truth.assert_().that(lvProgress.observedValues).isEmpty()
        Truth.assert_().that(currentWeather.observedValues).isEmpty()
    }

    @Test
    fun `check Weather`() {
        Mockito.`when`(weatherUseCase.provideCurrentLocation())
                .thenReturn(Single.just(Weather("city", 34.3, Date(5465443))))
        Mockito.`when`(weatherUseCase.provideWeatherForecastLocation())
                .thenReturn(Single.just(listOf(
                        Weather("city", 34.3, Date(5465443)),
                        Weather("city", 34.3, Date(5465443)),
                        Weather("city", 34.3, Date(5465443))
                )))

        val currentWeather = classUnderTest.lvCurrentWeather.testObserver()
        val lvWeatherForecast = classUnderTest.lvWeatherForecast.testObserver()
        val lvProgress = classUnderTest.lvScreenState.testObserver()

        classUnderTest.updateWeather()

        Truth.assert_().that(lvProgress.observedValues).isEqualTo(
                listOf(Progress(), Loaded())
        )
        Truth.assert_().that(currentWeather.observedValues).isNotEmpty()
        Truth.assert_().that(lvWeatherForecast.observedValues).isNotEmpty()
        Mockito.verify(weatherUseCase).provideCurrentLocation()

    }

    @Test
    fun `check error`() {
        Mockito.`when`(weatherUseCase.provideCurrentLocation())
                .thenReturn(Single.error(RuntimeException()))

        val currentWeather = classUnderTest.lvCurrentWeather.testObserver()
        val lvWeatherForecast = classUnderTest.lvWeatherForecast.testObserver()
        val lvProgress = classUnderTest.lvScreenState.testObserver()

        classUnderTest.updateWeather()

        Truth.assert_().that(lvProgress.observedValues).isEqualTo(
                listOf(Progress(), ErrorState(RuntimeException(), retry = {}))
        )
        Truth.assert_().that(currentWeather.observedValues).isEmpty()
        Truth.assert_().that(lvWeatherForecast.observedValues).isEmpty()
        Mockito.verify(weatherUseCase).provideCurrentLocation()
    }
}

