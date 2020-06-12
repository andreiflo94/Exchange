package com.heixss.exchange.di

import com.heixss.exchange.ExchangeApp
import com.heixss.exchange.di.modules.ActivityBindingModule
import com.heixss.exchange.di.modules.AppModule
import com.heixss.exchange.di.modules.FragmentBindingModule
import com.heixss.exchange.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        AppModule::class,
        FragmentBindingModule::class,
        ActivityBindingModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: ExchangeApp): Builder

        fun build(): AppComponent
    }

    fun inject(application: ExchangeApp)
}