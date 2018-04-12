package org.example.moex.data.source.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import org.example.moex.data.model.Share

@Database(entities = [Share::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shareDao(): ShareDao

}
