package com.heixss.exchange.viewmodels

import androidx.lifecycle.LiveData
import com.heixss.exchange.model.local.Rate
import com.heixss.exchange.model.remote.RatesResponse
import com.heixss.exchange.model.repositories.RatesRepository
import com.heixss.exchange.model.repositories.SharedPrefsRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: RatesRepository,
    private val sharedPrefsRepository: SharedPrefsRepository
) : BaseViewModel() {

    val ratesResponseSubject = PublishSubject.create<RatesResponse>()
    private lateinit var ratesDisposable: Disposable

    fun liveRates(): LiveData<List<Rate>> {
        return repository.liveRates(sharedPrefsRepository.getBaseCurrency())
    }

    /**
     * Method that starts loading the rates and then refreshes the rates
     * until stopLoading() is called
     */
    fun loadRates() {
        ratesDisposable = Observable.interval(
            sharedPrefsRepository.getHomeRefreshTime().toLong(),
            TimeUnit.SECONDS
        )
            .startWith(0)
            .flatMap {
                return@flatMap repository.loadRates(
                    sharedPrefsRepository.getBaseCurrency()
                )
                    .toObservable()
            }.doOnNext { ratesResponseSubject.onNext(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, { errorSubject.onNext(it.localizedMessage!!) })
    }

    /**
     * Method that will dispose the ratesDisposable so that the refresh loading will work
     * until stopLoading is called
     */
    fun stopLoading() {
        ratesDisposable.dispose()
    }

    fun getBaseCurrency(): String {
        return sharedPrefsRepository.getBaseCurrency()
    }

}
