package com.heixss.exchange.di.modules

import androidx.lifecycle.ViewModel
import com.heixss.exchange.di.ViewModelKey
import com.heixss.exchange.ui.fragments.ChartsViewModel
import com.heixss.exchange.viewmodels.HomeViewModel
import com.heixss.exchange.viewmodels.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChartsViewModel::class)
    abstract fun bindChartsViewModel(viewModel: ChartsViewModel): ViewModel
}
