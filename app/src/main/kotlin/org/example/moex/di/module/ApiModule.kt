package org.example.moex.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.example.moex.api.Api
import org.example.moex.di.scope.PerApp
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

/**
 * Created by 5turman on 23.03.2017.
 */
@Module
class ApiModule {

    @Provides
    @PerApp
    fun provideApi(): Api {

        val httpClient = OkHttpClient.Builder()
                .addInterceptor(
                        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
                )
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://iss.moex.com/iss/")
                .client(httpClient)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
                )
                .build()

        return retrofit.create(Api::class.java)
    }

}
