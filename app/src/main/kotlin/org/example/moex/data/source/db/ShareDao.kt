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
    suspend fun getAll(): List<Share>

    @Query("SELECT * FROM shares WHERE id = :id")
    suspend fun get(id: String): Share?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(share: Share)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun put(shares: List<Share>)

}
