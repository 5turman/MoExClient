package org.example.moex.data

import io.reactivex.Observable
import org.example.moex.data.model.Share

/**
 * Created by 5turman on 20.04.2017.
 */
interface SharesRepository {

    suspend fun getAll(forceUpdate: Boolean): List<Share>

    /**
     * @param period in seconds
     */
    fun get(shareId: String, period: Int = 5): Observable<Share>

}
