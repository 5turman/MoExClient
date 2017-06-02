package org.example.moex.data.source

import io.reactivex.Single
import org.example.moex.data.model.Share

/**
 * Created by 5turman on 29.03.2017.
 */
interface SharesDataSource {

    fun getAll(): Single<List<Share>>

    fun get(id: String): Single<Share>

    fun put(share: Share)

    fun put(shares: List<Share>)

}
