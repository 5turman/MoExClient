package org.example.moex.data

import io.reactivex.Observable
import org.example.moex.data.model.Share

/**
 * Created by 5turman on 20.04.2017.
 */
interface SharesRepository {

    fun getAll(forceUpdate: Boolean): Observable<List<Share>>

}
