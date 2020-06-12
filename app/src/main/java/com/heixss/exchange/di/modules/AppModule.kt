package com.heixss.exchange.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.heixss.exchange.ExchangeApp
import com.heixss.exchange.db.AppDatabase
import com.heixss.exchange.network.ExchangeApi
import com.heixss.exchange.network.HeadersInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(app: ExchangeApp): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideExchangeApi(retrofit: Retrofit): ExchangeApi {
        return retrofit.create(ExchangeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.exchangeratesapi.io/")
            // Moshi maps JSON to classes
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache, headersInterceptor: HeadersInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(headersInterceptor)
        client.addInterceptor(
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        client.cache(cache)
        client.connectionPool(ConnectionPool(5, 3, TimeUnit.SECONDS))
        return client.build()
    }

    @Singleton
    @Provides
    fun provideOkHttpCache(context: Context): Cache {
        return Cache(File(context.cacheDir, "okhttp-cache"), (3 * 1000 * 1000).toLong())
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "exchange-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.packageName + "shared_preferences.persist",
            Context.MODE_PRIVATE
        )
    }

}