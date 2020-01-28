package org.example.moex.data.source.local

import org.example.moex.data.model.Share
import org.example.moex.data.source.SharesDataSource
import org.example.moex.data.source.db.ShareDao

/**
 * Created by 5turman on 29.03.2017.
 */
class SharesLocalDataSource constructor(private val dao: ShareDao) : SharesDataSource {

    override suspend fun get(id: String): Share = dao.get(id)

    override suspend fun getAll(): List<Share> = dao.getAll()

    override suspend fun put(share: Share) {
        dao.put(share)
    }

    override suspend fun put(shares: List<Share>) {
        dao.put(shares)
    }

}
