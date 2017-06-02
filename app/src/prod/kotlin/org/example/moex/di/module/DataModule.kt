package org.example.moex.di.module

import dagger.Binds
import dagger.Module
import org.example.moex.data.SharesRepository
import org.example.moex.data.SharesRepositoryImpl
import org.example.moex.data.source.SharesDataSource
import org.example.moex.data.source.local.SharesLocalDataSource
import org.example.moex.data.source.remote.SharesRemoteDataSource
import org.example.moex.di.qualifier.Local
import org.example.moex.di.qualifier.Remote
import org.example.moex.di.scope.PerApp

/**
 * Created by 5turman on 13.04.2017.
 */
@Module
abstract class DataModule {

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
