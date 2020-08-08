package com.deveshmittal.presentation.ui.screens

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.deveshmittal.presentation.R
import com.deveshmittal.presentation.databinding.RhWetherBinding
import com.deveshmittal.presentation.mvvm.model.WeatherUi

class WeatherAdpater : RecyclerView.Adapter<WeatherAdpater.WeatherForecastHolder>() {
    private val mRegionsList: ArrayList<WeatherUi> = ArrayList()


    override fun getItemCount(): Int = mRegionsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdpater.WeatherForecastHolder {
        val binding = DataBindingUtil.inflate<RhWetherBinding>(LayoutInflater.from(parent.context),
                R.layout.rh_wether, parent, false)
        return WeatherForecastHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherAdpater.WeatherForecastHolder, position: Int) {
        holder.binding.weather = mRegionsList.get(position)
    }

    fun subpmitUpdate(regionsList: List<WeatherUi>?) {
        val difCallback = RegionDiffCallback(mRegionsList, regionsList ?: listOf())
        val diffResult = DiffUtil.calculateDiff(difCallback)
        this.mRegionsList.clear()
        this.mRegionsList.addAll(difCallback.new)
        diffResult.dispatchUpdatesTo(this)
    }

    class WeatherForecastHolder(val binding: RhWetherBinding) : RecyclerView.ViewHolder(binding.root)

    class RegionDiffCallback constructor(val old: List<WeatherUi>,
                                         val new: List<WeatherUi>) : DiffUtil.Callback() {


        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = old.get(oldItemPosition)
            val new = new.get(newItemPosition)
            return old == new
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = old.get(oldItemPosition)
            val new = new.get(newItemPosition)
            return old.equals(new)
        }

        override fun getOldListSize(): Int = old.size

        override fun getNewListSize(): Int = new.size
        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return new.get(newItemPosition)
        }
    }

}