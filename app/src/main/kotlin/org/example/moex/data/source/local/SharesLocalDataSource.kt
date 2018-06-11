package org.example.moex.data.source.local

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.example.moex.data.model.Share
import org.example.moex.data.source.SharesDataSource
import org.example.moex.data.source.db.ShareDao
import org.example.moex.di.scope.PerApp
import javax.inject.Inject

/**
 * Created by 5turman on 29.03.2017.
 */
@PerApp
class SharesLocalDataSource
@Inject constructor(private val dao: ShareDao) : SharesDataSource {

    override fun get(id: String): Maybe<Share> = dao.get(id).subscribeOn(Schedulers.io())

    override fun getAll(): Single<List<Share>> = dao.getAll().subscribeOn(Schedulers.io())

    override fun put(share: Share): Completable = Completable.defer {
        dao.put(share)
        Completable.complete()
    }.subscribeOn(Schedulers.io())

    override fun put(shares: List<Share>): Completable = Completable.defer {
        dao.put(shares)
        Completable.complete()
    }.subscribeOn(Schedulers.io())

}
