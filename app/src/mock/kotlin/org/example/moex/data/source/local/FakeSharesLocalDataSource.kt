package org.example.moex.data.source.local

import org.example.moex.data.model.Share
import org.example.moex.data.source.SharesDataSource

/**
 * Created by 5turman on 13.04.2017.
 */
object FakeSharesLocalDataSource : SharesDataSource {

    private val shares = mutableListOf<Share>()

    override suspend fun getAll(): List<Share> {
        return shares
    }

    override suspend fun get(id: String): Share {
        TODO("not implemented")
    }

    override suspend fun put(share: Share) {
        TODO("not implemented")
    }

    override suspend fun put(shares: List<Share>) {
        this.shares.addAll(shares)
    }

    fun reset() {
        shares.clear()
    }

}
