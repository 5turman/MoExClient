package org.example.moex.data.source.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
