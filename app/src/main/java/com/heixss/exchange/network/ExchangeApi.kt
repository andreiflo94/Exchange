package com.heixss.exchange.network

import com.heixss.exchange.model.remote.ChartRatesResponse
import com.heixss.exchange.model.remote.RatesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeApi {

    @GET("latest")
    fun getRates(
        @Query("base")
        baseCurrency: String
    ): Single<RatesResponse>

    @GET("history")
    fun getChartRates(
        @Query("start_at")
        startAt: String,
        @Query("end_at")
        endAt: String,
        @Query("symbols") symbols: String,
        @Query("base") base: String = "EUR"
    ): Single<ChartRatesResponse>
}