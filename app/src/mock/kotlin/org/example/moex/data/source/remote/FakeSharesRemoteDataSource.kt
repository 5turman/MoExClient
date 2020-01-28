package org.example.moex.data.source.remote

import org.example.moex.data.model.Share
import org.example.moex.data.source.SharesDataSource

/**
 * Created by 5turman on 20.04.2017.
 */
object FakeSharesRemoteDataSource : SharesDataSource {

    private val shares = mutableListOf<Share>()
    var error: Throwable? = null

    override suspend fun getAll(): List<Share> {
        val e = error
        if (e != null) throw e
        return shares
    }

    override suspend fun get(id: String): Share {
        TODO("not implemented")
    }

    override suspend fun put(share: Share) {
        TODO("not implemented")
    }

    override suspend fun put(shares: List<Share>) {
        TODO("not implemented")
    }

    fun reset() {
        shares.clear()
        error = null
    }

}
