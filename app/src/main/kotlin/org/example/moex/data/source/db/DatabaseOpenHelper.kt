package org.example.moex.data.source.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.example.moex.di.scope.PerApp
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import javax.inject.Inject

/**
 * Created by 5turman on 30.03.2017.
 */
@PerApp
class DatabaseOpenHelper @Inject constructor(context: Context) :
        ManagedSQLiteOpenHelper(context, "app.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SharesTable.createStatement())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

}
