package com.heixss.exchange.model.repositories

import androidx.lifecycle.LiveData
import com.heixss.exchange.db.AppDatabase
import com.heixss.exchange.model.local.ChartData
import com.heixss.exchange.model.local.Currency
import com.heixss.exchange.model.local.Rate
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

    /**
     * Method that fetches the response from the api, then the response is parsed into
     * a array of Rate objects. Then the array will be stored in the local db.
     */
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

    /**
     * Method that fetches the response from the api, then the response is parsed into
     * a array of ChartData objects. Then the method will return a Single that emits
     * this array.
     */
    fun loadChartData(startDate: String, endDate: String, symbol: String): Single<List<ChartData>> {
        return api.getChartRates(startDate, endDate, symbols = symbol).subscribeOn(Schedulers.io())
            .map { response ->
                val chartDataList = ArrayList<ChartData>()
                chartDataList.addAll(parseDateMapEntry(response.rates))
                return@map chartDataList
            }
    }

    /**
     * response.rates has a map as value and each entry has a map as value for each key eg:
     *  "rates":{
     *      "2018-05-04":{},
     *      "2018-08-27":{},
     *      "2018-06-08":{
     *      "JPY":128.64,
     *      "ILS":4.2009
     *      },
     *  }
     *  I've named "2018-05-04":{}, as dateMapEntry
     *  and "JPY":128.64, as ratesMapEntry
     */
    private fun parseDateMapEntry(rates: Map<String, Map<String, String>>): ArrayList<ChartData> {
        val chartDataList = ArrayList<ChartData>()
        rates.forEach { dateMapEntry ->
            val date = dateMapEntry.key
            val dateMap = dateMapEntry.value
            chartDataList.addAll(parseRatesMapEntry(date, dateMap))
        }
        return chartDataList
    }

    private fun parseRatesMapEntry(
        date: String,
        dateMap: Map<String, String>
    ): ArrayList<ChartData> {
        val chartDataList = ArrayList<ChartData>()
        dateMap.forEach { ratesMapEntry ->
            var chartData: ChartData? = null
            when (ratesMapEntry.key) {
                "RON" -> {
                    chartData = ChartData(
                        day = date,
                        value = ratesMapEntry.value.toDouble(),
                        currency = "RON"
                    )
                }
                "USD" -> {
                    chartData = ChartData(
                        day = date,
                        value = ratesMapEntry.value.toDouble(),
                        currency = "USD"
                    )
                }
                "BGN" -> {
                    chartData = ChartData(
                        day = date,
                        value = ratesMapEntry.value.toDouble(),
                        currency = "BGN"
                    )
                }
            }
            chartData?.let { chartDataList.add(it) }
        }
        return chartDataList
    }
}