package com.heixss.exchange.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.heixss.exchange.model.local.Rate

@Dao
abstract class RateDao : BaseDao<Rate> {

    @Query("SELECT * FROM Rate WHERE base =:base ORDER by currency")
    abstract fun getRates(base: String): LiveData<List<Rate>>

    @Query("DELETE FROM Rate")
    abstract fun clearRates()

}