package org.example.moex.data

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.example.moex.core.QuotesStorage
import org.example.moex.data.model.Share
import org.example.moex.data.source.SharesDataSource
import java.util.concurrent.TimeUnit

/**
 * Created by 5turman on 22.03.2017.
 */
class SharesRepositoryImpl constructor(
    private val localDataSource: SharesDataSource,
    private val remoteDataSource: SharesDataSource,
    private val quotesStorage: QuotesStorage
) :
    SharesRepository {

    @Volatile
    private var cache = emptyMap<String, Share>()

    override fun getAll(forceUpdate: Boolean): Observable<List<Share>> {
        if (cache.isNotEmpty()) {
            var observable = Observable.just(cache.values.toList())

            if (forceUpdate) {
                observable = observable
                    .concatWith(
                        loadSharesByApi().doOnNext { shares ->
                            cache = buildMap(shares)
                        }
                    ).subscribeOn(Schedulers.io())
            }

            return observable
        }
        return localDataSource.getAll()
            .flatMapObservable { shares ->
                when {
                    shares.isEmpty() -> loadSharesByApi()
                    forceUpdate -> Observable.just(shares).concatWith(loadSharesByApi())
                    else -> Observable.just(shares)
                }
            }
            .doOnNext { shares ->
                cache = buildMap(shares)
            }
            .subscribeOn(Schedulers.io())
    }

    override fun get(shareId: String, period: Int): Observable<Share> =
        remoteDataSource.get(shareId)
            .repeatWhen { source -> source.delay(period.toLong(), TimeUnit.SECONDS) }
            .doOnNext { share ->
                cache += (share.id to share)
                localDataSource.put(share).blockingAwait()
                quotesStorage.getWriter(shareId).use {
                    it.write(share.last, share.timestamp)
                }
            }
            .toObservable()

    private fun loadSharesByApi() =
        remoteDataSource.getAll()
            .doOnSuccess { shares ->
                localDataSource.put(shares).blockingAwait()
            }
            .toObservable()

    private fun buildMap(shares: List<Share>): Map<String, Share> {
        val map = mutableMapOf<String, Share>()
        shares.forEach {
            map[it.id] = it
        }
        return map
    }

}
