package org.example.moex.di

import android.content.Context
import androidx.room.Room
import org.example.moex.core.QuotesStorage
import org.example.moex.data.SharesRepository
import org.example.moex.data.SharesRepositoryImpl
import org.example.moex.data.source.SharesDataSource
import org.example.moex.data.source.db.AppDatabase
import org.example.moex.data.source.local.SharesLocalDataSource
import org.example.moex.data.source.remote.SharesRemoteDataSource
import org.example.moex.di.local
import org.example.moex.di.remote
import org.koin.dsl.module
import java.io.File

val dataModule = module {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "app.db").build() }
    factory { get<AppDatabase>().shareDao() }

    single<SharesDataSource>(qualifier = local) { SharesLocalDataSource(get()) }
    single<SharesDataSource>(qualifier = remote) { SharesRemoteDataSource(get(), get()) }

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
