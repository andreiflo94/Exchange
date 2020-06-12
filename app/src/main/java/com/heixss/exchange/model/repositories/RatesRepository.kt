package com.heixss.exchange.model.repositories

import androidx.lifecycle.LiveData
import com.heixss.exchange.db.AppDatabase
import com.heixss.exchange.model.local.Currency
import com.heixss.exchange.model.local.Rate
import com.heixss.exchange.model.remote.HistoricRatesResponse
import com.heixss.exchange.model.remote.RatesResponse
import com.heixss.exchange.network.ExchangeApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RatesRepository @Inject constructor(
    private val api: ExchangeApi,
    private val appDatabase: AppDatabase
) {

    fun liveRates(baseCurrency: String): LiveData<List<Rate>> {
        return appDatabase.rateDao().getRates(baseCurrency)
    }

    fun liveCurrencies(): LiveData<List<Currency>> {
        return appDatabase.currencyDao().getCurrencies()
    }

    fun loadRates(
        baseCurrency: String
    ): Single<RatesResponse> {
        return api.getRates(baseCurrency).subscribeOn(Schedulers.io())
            .map { response ->
                val rates = ArrayList<Rate>()
                val currencies = ArrayList<Currency>()
                response.rates.forEach { map ->
                    rates.add(Rate(map.key, map.value, response.base))
                    currencies.add(Currency(map.key))
                }
                currencies.add(Currency(baseCurrency))
                appDatabase.currencyDao().insert(*currencies.toTypedArray())
                appDatabase.rateDao().insert(*rates.toTypedArray())
                return@map response
            }
    }

    fun loadHistoricData(startDate: String, endDate: String): Single<HistoricRatesResponse>{
        return api.getHistoricRates(startDate, endDate).subscribeOn(Schedulers.io())
            .map{

                return@map it
            }
    }
}