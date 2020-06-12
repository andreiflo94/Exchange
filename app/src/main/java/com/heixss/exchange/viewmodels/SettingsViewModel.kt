package com.heixss.exchange.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.heixss.exchange.model.repositories.RatesRepository
import com.heixss.exchange.model.repositories.SharedPrefsRepository
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val ratesRepository: RatesRepository,
    private val sharedPrefsRepository: SharedPrefsRepository
) :
    BaseViewModel() {

    fun getBaseCurrency(): String {
        return sharedPrefsRepository.getBaseCurrency()
    }

    fun setBaseCurrency(baseCurrency: String) {
        sharedPrefsRepository.setBaseCurrency(baseCurrency)
    }

    fun getHomeRefreshTime(): Int {
        return sharedPrefsRepository.getHomeRefreshTime()
    }

    fun setHomeRefreshTime(refreshTime: Int) {
        sharedPrefsRepository.setHomeRefreshTime(refreshTime)
    }

    fun liveCurrencies(): LiveData<List<String>> {
        return Transformations.map(ratesRepository.liveCurrencies()) { currencies ->
            val currencyStringList = ArrayList<String>()
            currencies.forEach {
                currencyStringList.add(it.currency)
            }
            return@map currencyStringList
        }
    }
}
