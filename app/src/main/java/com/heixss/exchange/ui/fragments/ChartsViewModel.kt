package com.heixss.exchange.ui.fragments

import android.util.Log
import com.heixss.exchange.model.repositories.RatesRepository
import com.heixss.exchange.viewmodels.BaseViewModel
import com.heixss.exchange.viewmodels.Progress
import java.time.LocalDate
import javax.inject.Inject

class ChartsViewModel @Inject constructor(private val ratesRepository: RatesRepository) :
    BaseViewModel() {
    fun loadHistoricResponse() {
        val today: LocalDate = LocalDate.now()
        val tenDaysAgo = today.minusDays(10)
        disposables.add(ratesRepository.loadHistoricData(tenDaysAgo.toString(), today.toString())
            .doOnSubscribe { refreshSubject.onNext(Progress.SHOW) }
            .doFinally { refreshSubject.onNext(Progress.HIDE) }
            .subscribe(
                {
                    Log.d("test", "test")
                },
                {
                    errorSubject.onNext(it.localizedMessage!!)
                })
        )
    }
}
