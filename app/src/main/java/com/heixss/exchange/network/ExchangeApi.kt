package com.heixss.exchange.network

import com.heixss.exchange.model.remote.HistoricRatesResponse
import com.heixss.exchange.model.remote.RatesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExchangeApi {

    @GET("latest")
    fun getRates(
        @Query("base")
        baseCurrency: String
    ): Single<RatesResponse>

    @GET("history")
    fun getHistoricRates(
        @Query("start_at")
        startAt: String,
        @Query("end_at")
        endAt: String
    ): Single<HistoricRatesResponse>
}