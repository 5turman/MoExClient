package org.example.moex.api

import org.example.moex.api.dto.CandlesDTO
import org.example.moex.api.dto.SharesDTO
import org.example.moex.data.model.Interval
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by 5turman on 23.03.2017.
 */
interface Api {

    @GET("engines/stock/markets/shares/boards/TQBR/securities.xml")
    suspend fun getShares(@Query("index") index: String?): SharesDTO

    @GET("engines/stock/markets/shares/boards/TQBR/securities/{id}.xml")
    suspend fun getShare(@Path("id") shareId: String): SharesDTO

    @GET("engines/stock/markets/shares/securities/{id}/candles.xml")
    suspend fun getCandles(
        @Path("id") shareId: String,
        @Query("from") from: String? = null,
        @Query("till") till: String? = null,
        @Query("interval") interval: Interval? = null
    ): CandlesDTO
}
