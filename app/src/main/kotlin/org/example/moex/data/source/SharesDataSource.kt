package org.example.moex.data.source

import org.example.moex.data.model.Share

/**
 * Created by 5turman on 29.03.2017.
 */
interface SharesDataSource {

    suspend fun getAll(): List<Share>

    suspend fun get(id: String): Share

    suspend fun put(share: Share)

    suspend fun put(shares: List<Share>)

}
