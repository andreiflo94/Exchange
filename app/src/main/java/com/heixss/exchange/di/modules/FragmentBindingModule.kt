package com.heixss.exchange.di.modules

import com.heixss.exchange.ui.fragments.ChartsFragment
import com.heixss.exchange.ui.fragments.HomeFragment
import com.heixss.exchange.ui.fragments.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun bindSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun bindChartsFragment(): ChartsFragment

}