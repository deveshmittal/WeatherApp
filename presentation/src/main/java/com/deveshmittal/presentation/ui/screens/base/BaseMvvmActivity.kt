package com.deveshmittal.presentation.ui.screens.base

import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import javax.inject.Inject

abstract class BaseMvvmActivity<T : ViewDataBinding>(layoutId: Int) :
        BaseActivity(layoutId) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: T

    abstract fun attachViewModels(binding: T)

    override fun setContentView() {
        binding = DataBindingUtil.setContentView<T>(this, layoutId)
        binding.lifecycleOwner = this
        attachViewModels(binding)
    }

}