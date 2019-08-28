package org.example.moex.di.module

import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import dagger.Module
import dagger.Provides
import org.example.moex.data.source.db.AppDatabase
import org.example.moex.di.scope.PerApp

/**
 * Created by 5turman on 23.03.2017.
 */
@Module
class AppModule(private val appContext: Context) {

    @Provides
    fun provideContext() = appContext

    @Provides
    fun provideResources(): Resources = appContext.resources

    @Provides
    @PerApp
    fun provideDatabase() =
        Room.databaseBuilder(appContext, AppDatabase::class.java, "app.db").build()


    @Provides
    fun provideShareDao(db: AppDatabase) = db.shareDao()

}
