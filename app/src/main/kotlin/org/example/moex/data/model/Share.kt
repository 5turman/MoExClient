package org.example.moex.data.model

/**
 * Created by 5turman on 22.03.2017.
 */
class Share(
        val id: String,
        val name: String,
        val shortName: String,
        val timestamp: Long,
        val last: Double,
        val lastToPrev: Double
)
