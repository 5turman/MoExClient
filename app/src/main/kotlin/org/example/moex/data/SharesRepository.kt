package org.example.moex.data

import kotlinx.coroutines.flow.Flow
import org.example.moex.data.model.Share
import java.util.concurrent.TimeUnit

/**
 * Created by 5turman on 20.04.2017.
 */
interface SharesRepository {

    suspend fun getAll(forceUpdate: Boolean): List<Share>

    /**
     * @param period of repeated request
     */
    fun get(shareId: String, period: Long = 5, timeUnit: TimeUnit = TimeUnit.SECONDS): Flow<Share>

}
