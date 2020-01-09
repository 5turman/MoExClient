package org.example.moex.di

import android.content.Context
import org.example.moex.core.QuotesStorage
import org.example.moex.data.SharesRepository
import org.example.moex.data.SharesRepositoryImpl
import org.example.moex.data.source.SharesDataSource
import org.example.moex.data.source.local.FakeSharesLocalDataSource
import org.example.moex.data.source.remote.FakeSharesRemoteDataSource
import org.koin.dsl.module
import java.io.File

val dataModule = module {
    single<SharesDataSource>(qualifier = local) { FakeSharesLocalDataSource }
    single<SharesDataSource>(qualifier = remote) { FakeSharesRemoteDataSource }

    single<SharesRepository> {
        SharesRepositoryImpl(
            get(qualifier = local),
            get(qualifier = remote),
            get()
        )
    }

    single {
        val context = get<Context>()
        val dir = File(context.filesDir, "quotes").apply { mkdirs() }
        QuotesStorage(dir)
    }
}
