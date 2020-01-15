package org.example.moex.data.source

import io.reactivex.Completable
import org.example.moex.data.model.Share

/**
 * Created by 5turman on 29.03.2017.
 */
interface SharesDataSource {

    suspend fun getAll(): List<Share>

    suspend fun get(id: String): Share?

    fun put(share: Share): Completable

    suspend fun put(shares: List<Share>)

}
