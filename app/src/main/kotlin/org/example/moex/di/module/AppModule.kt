package org.example.moex.di.module

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides

/**
 * Created by 5turman on 23.03.2017.
 */
@Module
class AppModule(private val appContext: Context) {

    @Provides
    fun provideContext() = appContext

    @Provides
    fun provideResources(): Resources = appContext.resources

}
