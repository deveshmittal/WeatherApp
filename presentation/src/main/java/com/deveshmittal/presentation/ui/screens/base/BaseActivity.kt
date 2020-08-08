package com.deveshmittal.presentation.ui.screens.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import dagger.android.support.DaggerAppCompatActivity

open class BaseActivity(@LayoutRes open val layoutId: Int) :
        DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
    }

    open fun setContentView() {
        setContentView(this.layoutId)
    }


}