package org.example.moex.data

import io.reactivex.Observable
import org.example.moex.data.model.Share
import org.example.moex.data.source.SharesDataSource

/**
 * Created by 5turman on 22.03.2017.
 */
class SharesRepositoryImpl constructor(
    private val localDataSource: SharesDataSource,
    private val remoteDataSource: SharesDataSource
) : SharesRepository {

    override suspend fun getAll(forceUpdate: Boolean): List<Share> {
        if (!forceUpdate) {
            val localShares = localDataSource.getAll()
            if (localShares.isNotEmpty()) {
                return localShares
            }
        }

        return remoteDataSource.getAll().also {
            localDataSource.put(it)
        }
    }

    override fun get(shareId: String, period: Int): Observable<Share> =
        Observable.empty()
//        remoteDataSource.get(shareId)
//            .repeatWhen { source -> source.delay(period.toLong(), TimeUnit.SECONDS) }
//            .doOnNext { share ->
//                cache[share.id] = share
//                localDataSource.put(share).blockingAwait()
//                quotesStorage.getWriter(shareId).use {
//                    it.write(share.last, share.timestamp)
//                }
//            }
//            .toObservable()

}
