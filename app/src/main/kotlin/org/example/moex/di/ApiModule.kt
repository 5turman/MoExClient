package org.example.moex.di

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.example.moex.api.Api
import org.example.moex.api.ApiErrorHandler
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

val apiModule = module {
    fun buildHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()

    fun buildRetrofit() =
        Retrofit.Builder()
            .baseUrl("https://iss.moex.com/iss/")
            .client(buildHttpClient())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
            )
            .build()

    single { buildRetrofit().create(Api::class.java) }
    single { ApiErrorHandler() }
}
