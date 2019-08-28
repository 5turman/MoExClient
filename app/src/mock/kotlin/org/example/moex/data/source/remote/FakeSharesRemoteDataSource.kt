package org.example.moex.data.source.remote

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.example.moex.data.model.Share
import org.example.moex.data.source.SharesDataSource

/**
 * Created by 5turman on 20.04.2017.
 */
object FakeSharesRemoteDataSource : SharesDataSource {

    private val shares = mutableListOf<Share>()
    var error: Throwable? = null

    override fun getAll(): Single<List<Share>> {
        if (error != null) {
            return Single.error(error)
        }
        return Single.just(shares)
    }

    override fun get(id: String): Maybe<Share> {
        TODO("not implemented")
    }

    override fun put(share: Share): Completable {
        TODO("not implemented")
    }

    override fun put(shares: List<Share>): Completable {
        TODO("not implemented")
    }

    fun reset() {
        shares.clear()
        error = null
    }

}
