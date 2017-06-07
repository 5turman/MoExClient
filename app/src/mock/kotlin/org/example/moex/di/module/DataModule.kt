package org.example.moex.di.module

import dagger.Module
import dagger.Provides
import org.example.moex.data.SharesRepository
import org.example.moex.data.SharesRepositoryImpl
import org.example.moex.data.SharesRepositoryProxy
import org.example.moex.data.source.SharesDataSource
import org.example.moex.data.source.local.FakeSharesLocalDataSource
import org.example.moex.data.source.remote.FakeSharesRemoteDataSource
import org.example.moex.di.qualifier.Local
import org.example.moex.di.qualifier.Remote
import javax.inject.Provider

/**
 * Created by 5turman on 13.04.2017.
 */
@Module
class DataModule {

    @Provides
    @Local
    fun provideSharesLocalDataSource(): SharesDataSource = FakeSharesLocalDataSource

    @Provides
    @Remote
    fun provideSharesRemoteDataSource(): SharesDataSource = FakeSharesRemoteDataSource

    @Provides
    fun provideSharesRepository(provider: Provider<SharesRepositoryImpl>): SharesRepository =
            SharesRepositoryProxy.apply { this.provider = provider }

    @Provides
    @PerApp
    fun quotesStorage(context: Context): QuotesStorage {
        val dir = File(context.filesDir, "quotes").apply { mkdirs() }
        return QuotesStorage(dir)
    }

}
