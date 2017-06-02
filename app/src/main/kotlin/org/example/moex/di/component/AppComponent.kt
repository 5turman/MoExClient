package org.example.moex.di.component

import dagger.Component
import org.example.moex.di.module.ApiModule
import org.example.moex.di.module.AppModule
import org.example.moex.di.module.DataModule
import org.example.moex.di.scope.PerApp
import org.example.moex.ui.share.ShareActivity
import org.example.moex.ui.shares.SharesFragment

/**
 * Created by 5turman on 23.03.2017.
 */
@PerApp
@Component(modules = arrayOf(AppModule::class, ApiModule::class, DataModule::class))
interface AppComponent {

    fun inject(activity: ShareActivity)

    fun inject(fragment: SharesFragment)

}
