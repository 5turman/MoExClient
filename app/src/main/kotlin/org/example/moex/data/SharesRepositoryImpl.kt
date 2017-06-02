package org.example.moex.data

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.example.moex.data.model.Share
import org.example.moex.data.source.SharesDataSource
import org.example.moex.di.qualifier.Local
import org.example.moex.di.qualifier.Remote
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by 5turman on 22.03.2017.
 */
class SharesRepositoryImpl @Inject constructor(
        private @Local val localDataSource: SharesDataSource,
        private @Remote val remoteDataSource: SharesDataSource) : SharesRepository {

    private @Volatile var cache = emptyMap<String, Share>()

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
                    if (shares.isEmpty()) {
                        loadSharesByApi()
                    } else if (forceUpdate) {
                        Observable.just(shares).concatWith(loadSharesByApi())
                    } else {
                        Observable.just(shares)
                    }
                }
                .doOnNext { shares ->
                    cache = buildMap(shares)
                }
                .subscribeOn(Schedulers.io())
    }

    override fun get(shareId: String, interval: Int): Observable<Share> =
            Observable.interval(0, 5, TimeUnit.SECONDS)
                    .flatMap {
                        remoteDataSource.get(shareId)
                                .doOnSuccess {
                                    cache += (it.id to it)
                                    localDataSource.put(it)
                                }
                                .toObservable()
                    }

    private fun loadSharesByApi() =
            remoteDataSource.getAll()
                    .doOnSuccess { shares ->
                        localDataSource.put(shares)
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
