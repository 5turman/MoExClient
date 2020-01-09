package org.example.moex

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid
import org.example.moex.di.apiModule
import org.example.moex.di.appModule
import org.example.moex.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by 5turman on 23.03.2017.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        JodaTimeAndroid.init(this)

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(apiModule, dataModule, appModule))
        }
    }

}
