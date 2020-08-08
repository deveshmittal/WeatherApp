package com.deveshmittal.di.module.vm

/**
 * Created by Devesh Mittal on 10.06.2019.
 *
 */

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import com.deveshmittal.di.PerApp
import javax.inject.Inject
import javax.inject.Provider

@PerApp
class WheatherViewModelFactory @Inject constructor(
        private val creators: Map<Class<out ViewModel>,
                @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}