package org.example.moex.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by 5turman on 22.03.2017.
 */
@Entity(tableName = "shares")
data class Share(
    @PrimaryKey
    val id: String,
    val name: String,
    @ColumnInfo(name = "short_name")
    val shortName: String,
    val timestamp: Long,
    val last: Double,
    @ColumnInfo(name = "last_to_prev")
    val lastToPrev: Double
)
