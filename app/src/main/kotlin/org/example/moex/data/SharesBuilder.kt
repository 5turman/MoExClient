package org.example.moex.data

import org.example.moex.api.dto.ShareDTO
import org.example.moex.api.dto.SharesDTO
import org.example.moex.core.toDoubleOrDefault
import org.example.moex.data.model.Share
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat

/**
 * Created by 5turman on 22.03.2017.
 */
object SharesBuilder : io.reactivex.functions.Function<SharesDTO, List<Share>> {

    private val sysTimeFormatter =
            DateTimeFormat
                    .forPattern("yyyy-MM-dd HH:mm:ss")
                    .withZone(DateTimeZone.forID("Europe/Moscow"))

    override fun apply(dto: SharesDTO): List<Share> {
        val map = mutableMapOf<String, ShareDTO>()

        dto.securities
                .forEach { share ->
                    map[share.id] = share
                }

        return dto.marketData
                .mapNotNull { data ->
                    map[data.id]?.run {
                        val sysDateTime = sysTimeFormatter.parseDateTime(data.sysTime)
                        val timeArray = data.time.split(":")
                        val dateTime = sysDateTime.withTime(
                                timeArray[0].toInt(), timeArray[1].toInt(), timeArray[2].toInt(), 0)

                        Share(
                                id,
                                name,
                                shortName,
                                dateTime.millis,
                                data.last.toDoubleOrDefault(0.0),
                                data.lastToPrevPrice
                        )
                    }
                }
    }

}
