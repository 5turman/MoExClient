package org.example.moex.data.source.remote

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.example.moex.api.Api
import org.example.moex.api.ApiErrorHandler
import org.example.moex.data.SharesBuilder
import org.example.moex.data.model.Share
import org.example.moex.data.source.SharesDataSource

/**
 * Created by 5turman on 29.03.2017.
 */
class SharesRemoteDataSource constructor(
    private val api: Api,
    private val errorHandler: ApiErrorHandler
) : SharesDataSource {

    override fun getAll(): Single<List<Share>> =
        api.getShares(null)
            .compose(errorHandler.cast())
            .map(SharesBuilder)

    override fun get(id: String): Maybe<Share> =
        api.getShare(id)
            .compose(errorHandler.cast())
            .map { dto -> SharesBuilder.apply(dto)[0] }
            .toMaybe()

    override fun put(share: Share): Completable {
        throw UnsupportedOperationException()
    }

    override fun put(shares: List<Share>): Completable {
        throw UnsupportedOperationException()
    }

}
