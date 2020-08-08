package com.deveshmittal.presentation.ui.screens.base

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.deveshmittal.presentation.di.Injectable
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes open val layoutId: Int)
    : DaggerFragment(), Injectable {


    lateinit var binding: T

    abstract fun attachViewModels(binding: T)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, layoutId, container,
                false, DataBindingUtil.getDefaultComponent())
        attachViewModels(binding)
        binding.lifecycleOwner = this
        return binding.root
    }


}