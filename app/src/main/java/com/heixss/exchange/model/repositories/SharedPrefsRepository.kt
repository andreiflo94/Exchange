package com.heixss.exchange.model.repositories

import android.content.SharedPreferences
import javax.inject.Inject

const val HOME_REFRESH_TIME_KEY = "HOME_REFRESH_TIME_KEY"
const val BASE_CURRENCY = "BASE_CURRENCY"
const val DEFAULT_BASE_CURRENCY = "EUR"
const val DEFAULT_REFRESH_TIME = 3 //seconds

class SharedPrefsRepository @Inject constructor(private val sharedPrefsRepository: SharedPreferences) {

    /**
     * Sets the home screen refresh time
     * @param time in seconds
     */
    fun setHomeRefreshTime(time: Int) {
        sharedPrefsRepository.edit().putInt(HOME_REFRESH_TIME_KEY, time).apply()
    }

    /**
     * Gets the home screen refresh time in seconds
     */
    fun getHomeRefreshTime(): Int {
        return sharedPrefsRepository.getInt(HOME_REFRESH_TIME_KEY, DEFAULT_REFRESH_TIME)
    }

    /**
     * Sets the base currency
     */
    fun setBaseCurrency(currency: String) {
        sharedPrefsRepository.edit().putString(BASE_CURRENCY, currency).apply()
    }

    /**
     * Gets the base currency
     * If not set before it will return const DEFAULT_BASE_CURRENCY
     */
    fun getBaseCurrency(): String {
        sharedPrefsRepository.getString(BASE_CURRENCY, DEFAULT_BASE_CURRENCY)?.let {
            return it
        } ?: run {
            return DEFAULT_BASE_CURRENCY
        }
    }
}