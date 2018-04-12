package org.example.moex.data.source.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Maybe
import io.reactivex.Single
import org.example.moex.data.model.Share

@Dao
interface ShareDao {

    @Query("SELECT * FROM shares")
    fun getAll(): Single<List<Share>>

    @Query("SELECT * FROM shares WHERE id = :id")
    fun get(id: String): Maybe<Share>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(share: Share)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(shares: List<Share>)

}
