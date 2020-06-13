package com.heixss.exchange.ui.fragments

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.heixss.exchange.viewmodels.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment : Fragment {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    protected val disposables = CompositeDisposable()

    constructor() : super()
    constructor(@LayoutRes layoutResId: Int) : super(layoutResId)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        disposables.clear()
        super.onPause()
    }
}