package org.example.moex.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.example.moex.api.Api
import org.koin.dsl.module
import retrofit2.Retrofit
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
            .build()

    single { buildRetrofit().create(Api::class.java) }
}
