package com.heixss.exchange.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.heixss.exchange.model.local.Currency

@Dao
abstract class CurrencyDao : BaseDao<Currency> {

    @Query("SELECT * FROM Currency ORDER by currency")
    abstract fun getCurrencies(): LiveData<List<Currency>>

    @Query("DELETE FROM Currency")
    abstract fun clearCurrencies()

}