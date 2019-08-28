package org.example.moex.data

import io.reactivex.Observable
import org.example.moex.data.model.Share
import javax.inject.Provider

/**
 * Created by 5turman on 20.04.2017.
 */
object SharesRepositoryProxy : SharesRepository {

    lateinit var provider: Provider<SharesRepositoryImpl>

    private var proxyObject: SharesRepository? = null

    override fun get(shareId: String, period: Int): Observable<Share> {
        TODO("not implemented")
    }

    override fun getAll(forceUpdate: Boolean): Observable<List<Share>> {
        if (proxyObject == null) {
            proxyObject = provider.get()
        }
        return proxyObject!!.getAll(forceUpdate)
    }

    fun reset() {
        proxyObject = null
    }

}
