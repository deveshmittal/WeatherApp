package com.deveshmittal.presentation.ui.screens

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.deveshmittal.presentation.R
import com.deveshmittal.presentation.databinding.ActivityMainBinding
import com.deveshmittal.presentation.mvvm.WeatherVM
import com.deveshmittal.presentation.ui.screens.base.BaseMvvmActivity
import kotlinx.android.synthetic.main.layer_wether.*

class MainActivity : BaseMvvmActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var weatherViewModel: WeatherVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewCompat.setElevation(recycler, 10f);
    }

    override fun attachViewModels(binding: ActivityMainBinding) {
        weatherViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(WeatherVM::class.java)

        binding.vm = weatherViewModel
        weatherViewModel.requestLocationPermission(this)

        subscribeForWeather()

    }

    private fun subscribeForWeather() {
        val adapter = WeatherAdpater()

        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = adapter


        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        val dividerThickness = resources.getDimensionPixelOffset(R.dimen.divider)
        val shapeDrawableForDivider = ShapeDrawable(RectShape())
        shapeDrawableForDivider.intrinsicHeight = dividerThickness
        shapeDrawableForDivider.paint.color = resources.getColor(R.color.divider)

        dividerItemDecoration.setDrawable(shapeDrawableForDivider)
        recycler.addItemDecoration(dividerItemDecoration)

        weatherViewModel.lvWeatherForecast.observe(this, Observer { list ->
            adapter.subpmitUpdate(list)
        })
    }


}
