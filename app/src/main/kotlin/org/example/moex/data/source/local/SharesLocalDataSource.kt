package org.example.moex.data.source.local

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.example.moex.data.model.Share
import org.example.moex.data.source.SharesDataSource
import org.example.moex.data.source.db.ShareDao

/**
 * Created by 5turman on 29.03.2017.
 */
class SharesLocalDataSource constructor(private val dao: ShareDao) : SharesDataSource {

    override suspend fun get(id: String): Share? = dao.get(id)

    override suspend fun getAll(): List<Share> = dao.getAll()

    override fun put(share: Share): Completable = Completable.defer {
        dao.put(share)
        Completable.complete()
    }.subscribeOn(Schedulers.io())

    override suspend fun put(shares: List<Share>) {
        dao.put(shares)
    }

}
