package org.example.moex.di.module

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import org.example.moex.core.QuotesStorage
import org.example.moex.data.SharesRepository
import org.example.moex.data.SharesRepositoryImpl
import org.example.moex.data.source.SharesDataSource
import org.example.moex.data.source.local.SharesLocalDataSource
import org.example.moex.data.source.remote.SharesRemoteDataSource
import org.example.moex.di.qualifier.Local
import org.example.moex.di.qualifier.Remote
import org.example.moex.di.scope.PerApp
import java.io.File

/**
 * Created by 5turman on 13.04.2017.
 */
@Module(includes = arrayOf(DataModule.Declarations::class))
class DataModule {

    @Module
    abstract class Declarations {

        @Binds
        @Local
        abstract fun bindSharesLocalDataSource(dataSource: SharesLocalDataSource): SharesDataSource

        @Binds
        @Remote
        abstract fun bindSharesRemoteDataSource(dataSource: SharesRemoteDataSource): SharesDataSource

        @Binds
        @PerApp
        abstract fun bindSharesRepository(repo: SharesRepositoryImpl): SharesRepository

    }

    @Provides
    @PerApp
    fun quotesStorage(context: Context): QuotesStorage {
        val dir = File(context.filesDir, "quotes").apply { mkdirs() }
        return QuotesStorage(dir)
    }

}
