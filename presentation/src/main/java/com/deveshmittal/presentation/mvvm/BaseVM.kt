package com.deveshmittal.presentation.mvvm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.olearis.presentation.ui.mvvm.event.LiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseVM : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    val lvError = MutableLiveData<LiveEvent<Throwable>>()


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun addDisposable(dis: Disposable) {
        compositeDisposable.add(dis)
    }

    fun postError(t: Throwable) {
        lvError.postValue(LiveEvent(t))
    }

}