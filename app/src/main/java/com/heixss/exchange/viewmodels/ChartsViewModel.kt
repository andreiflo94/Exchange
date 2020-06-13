package com.heixss.exchange.viewmodels

import androidx.lifecycle.MutableLiveData
import com.heixss.exchange.model.local.ChartData
import com.heixss.exchange.model.repositories.RatesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import java.time.LocalDate
import javax.inject.Inject

class ChartsViewModel @Inject constructor(private val ratesRepository: RatesRepository) :
    BaseViewModel() {

    val chartDisplayValue = BehaviorSubject.createDefault<Any>(ChartDisplayValue.RON)

    val liveChartData = MutableLiveData<List<ChartData>>()

    fun loadChartResponse(symbol: String) {
        val today: LocalDate = LocalDate.now()
        val tenDaysAgo = today.minusDays(10)
        disposables.add(ratesRepository.loadChartData(tenDaysAgo.toString(), today.toString(), symbol)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { refreshSubject.onNext(Progress.SHOW) }
            .doFinally { refreshSubject.onNext(Progress.HIDE) }
            .subscribe(
                { liveChartData.value = it },
                { errorSubject.onNext(it.localizedMessage!!) })
        )
    }
}

enum class ChartDisplayValue {
    USD,
    RON,
    BGN
}