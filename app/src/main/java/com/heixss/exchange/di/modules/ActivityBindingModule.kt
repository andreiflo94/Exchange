package com.heixss.exchange.di.modules

import com.heixss.exchange.ui.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}