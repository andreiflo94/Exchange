package com.heixss.exchange.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

open class BaseViewModel : ViewModel() {

    val errorSubject = PublishSubject.create<String>()
    val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}

enum class Progress {
    SHOW,
    HIDE
}