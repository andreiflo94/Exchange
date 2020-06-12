package com.heixss.exchange.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.heixss.exchange.model.local.Currency
import com.heixss.exchange.model.local.Rate

/**
 * This app doesn't use migrations.
 * If you need to change db structure just increment the db version and make sure fallbackToDestructiveMigration()
 */
@Database(entities = [Rate::class, Currency::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rateDao(): RateDao
    abstract fun currencyDao(): CurrencyDao
}