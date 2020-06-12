package com.heixss.exchange.model.remote

import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RatesResponse(
    @PrimaryKey @Json(name = "base")
    val base: String = "",
    @Json(name = "date")
    val date: String = "",
    @Json(name = "rates")
    val rates: Map<String, String>
)