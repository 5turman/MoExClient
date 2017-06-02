package org.example.moex.data.source.local

import android.database.sqlite.SQLiteDatabase
import io.reactivex.Single
import org.example.moex.data.model.Share
import org.example.moex.data.source.SharesDataSource
import org.example.moex.data.source.db.DatabaseOpenHelper
import org.example.moex.data.source.db.SharesTable
import org.example.moex.di.scope.PerApp
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.replace
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.transaction
import javax.inject.Inject

/**
 * Created by 5turman on 29.03.2017.
 */
@PerApp
class SharesLocalDataSource @Inject constructor(private val db: DatabaseOpenHelper) : SharesDataSource {

    private val rowParser = classParser<Share>()

    override fun getAll(): Single<List<Share>> {
        return Single.defer {
            db.use {
                val shares = select(SharesTable.name, *SharesTable.allColumns).parseList(rowParser)
                Single.just(shares)
            }
        }
    }

    override fun get(id: String): Single<Share> {
        TODO("not implemented")
    }

    override fun put(share: Share) {
        db.use { put(this, share) }
    }

    override fun put(shares: List<Share>) {
        db.use {
            transaction {
                shares.forEach { put(this, it) }
            }
        }
    }

    private fun put(db: SQLiteDatabase, share: Share) {
        val values = arrayOf(
                SharesTable.COLUMN_ID to share.id,
                SharesTable.COLUMN_NAME to share.name,
                SharesTable.COLUMN_SHORT_NAME to share.shortName,
                SharesTable.COLUMN_TIMESTAMP to share.timestamp,
                SharesTable.COLUMN_LAST to share.last,
                SharesTable.COLUMN_LAST_TO_PREV to share.lastToPrev
        )

        db.replace(SharesTable.name, *values)
    }

}
