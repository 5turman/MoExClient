package org.example.moex.data.source

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.example.moex.data.model.Share

/**
 * Created by 5turman on 29.03.2017.
 */
interface SharesDataSource {

    fun getAll(): Single<List<Share>>

    fun get(id: String): Maybe<Share>

    fun put(share: Share): Completable

    fun put(shares: List<Share>): Completable

}
