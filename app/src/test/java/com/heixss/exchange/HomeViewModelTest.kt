package com.heixss.exchange

import com.heixss.exchange.model.remote.RatesResponse
import com.heixss.exchange.model.repositories.RatesRepository
import com.heixss.exchange.model.repositories.SharedPrefsRepository
import com.heixss.exchange.viewmodels.HomeViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.concurrent.Callable


open class HomeViewModelTest {

    lateinit var ratesRepository: RatesRepository
    lateinit var sharedPrefsRepository: SharedPrefsRepository
    lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        sharedPrefsRepository = Mockito.mock(SharedPrefsRepository::class.java)
        ratesRepository = Mockito.mock(RatesRepository::class.java)
        `when`(sharedPrefsRepository.getHomeRefreshTime()).thenReturn(3)
        `when`(sharedPrefsRepository.getBaseCurrency()).thenReturn("EUR")
        homeViewModel = HomeViewModel(ratesRepository, sharedPrefsRepository)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }
    }

    @Test
    fun testLoadRates() {
        val ratesResponse = RatesResponse("EUR", "2020-06-13", mapOf("RON" to "4.5467"))
        `when`(ratesRepository.loadRates(sharedPrefsRepository.getBaseCurrency())).thenReturn(
            Single.just(
                ratesResponse
            )
        )
        homeViewModel.loadRates()
        homeViewModel.ratesResponseSubject.subscribe { if (it == ratesResponse) assertNotNull(it) }
    }
}
