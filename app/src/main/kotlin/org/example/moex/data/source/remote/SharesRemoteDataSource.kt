package org.example.moex.data.source.remote

import org.example.moex.api.Api
import org.example.moex.api.tryWithApiErrorHandler
import org.example.moex.data.SharesBuilder
import org.example.moex.data.model.Share
import org.example.moex.data.source.SharesDataSource

/**
 * Created by 5turman on 29.03.2017.
 */
class SharesRemoteDataSource constructor(
    private val api: Api
) : SharesDataSource {

    override suspend fun getAll(): List<Share> =
        tryWithApiErrorHandler {
            api.getShares(null)
                .let { SharesBuilder.build(it) }
        }

    override suspend fun get(id: String): Share =
        tryWithApiErrorHandler {
            api.getShare(id)
                .let { SharesBuilder.build(it)[0] }
        }

    override suspend fun put(share: Share) {
        throw UnsupportedOperationException()
    }

    override suspend fun put(shares: List<Share>) {
        throw UnsupportedOperationException()
    }

}
