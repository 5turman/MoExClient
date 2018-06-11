package org.example.moex

import android.app.Application
import android.content.Context
import net.danlew.android.joda.JodaTimeAndroid
import org.example.moex.di.component.AppComponent
import org.example.moex.di.component.DaggerAppComponent
import org.example.moex.di.module.AppModule

/**
 * Created by 5turman on 23.03.2017.
 */
class App : Application() {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        JodaTimeAndroid.init(this)

        component = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
    }

    companion object {
        fun component(context: Context) = (context.applicationContext as App).component
    }

}
