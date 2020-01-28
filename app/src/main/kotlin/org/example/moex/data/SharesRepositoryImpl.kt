package org.example.moex.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.moex.data.model.Share
import org.example.moex.data.source.SharesDataSource
import java.util.concurrent.TimeUnit

/**
 * Created by 5turman on 22.03.2017.
 */
class SharesRepositoryImpl constructor(
    private val localDataSource: SharesDataSource,
    private val remoteDataSource: SharesDataSource
) : SharesRepository {

    override suspend fun getAll(forceUpdate: Boolean): List<Share> {
        if (!forceUpdate) {
            val localShares = localDataSource.getAll()
            if (localShares.isNotEmpty()) {
                return localShares
            }
        }

        return remoteDataSource.getAll().also {
            localDataSource.put(it)
        }
    }

    override fun get(shareId: String, period: Long, timeUnit: TimeUnit): Flow<Share> =
        flow {
            while (true) {
                val share = remoteDataSource.get(shareId)
                localDataSource.put(share)
                emit(share)
                delay(timeUnit.toMillis(period))
            }

        }

}
