package org.example.moex.data.source.db

/**
 * Created by 5turman on 30.03.2017.
 */
object SharesTable : BaseTable("shares") {

    const val COLUMN_ID = "id"
    const val COLUMN_NAME = "name"
    const val COLUMN_SHORT_NAME = "short_name"
    const val COLUMN_TIMESTAMP = "timestamp"
    const val COLUMN_LAST = "last"
    const val COLUMN_LAST_TO_PREV = "last_to_prev"

    val allColumns = arrayOf(
            COLUMN_ID, COLUMN_NAME, COLUMN_SHORT_NAME, COLUMN_TIMESTAMP, COLUMN_LAST, COLUMN_LAST_TO_PREV
    )

    override fun createStatement() = """CREATE TABLE IF NOT EXISTS $name
    (
        $COLUMN_ID STRING NOT NULL PRIMARY KEY,
        $COLUMN_NAME TEXT NOT NULL,
        $COLUMN_SHORT_NAME TEXT NOT NULL,
        $COLUMN_TIMESTAMP INTEGER NOT NULL,
        $COLUMN_LAST REAL NOT NULL,
        $COLUMN_LAST_TO_PREV REAL NOT NULL
    );"""

}
