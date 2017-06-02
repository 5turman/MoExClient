package org.example.moex.data.model

/**
 * Created by 5turman on 6/1/2017.
 */
enum class Interval(val value: Int) {

    MINUTE(1),
    TEN_MINUTES(10),
    HOUR(60),
    DAY(24),
    WEEK(7),
    MONTH(31),
    QUARTER(4);

    override fun toString() = value.toString()

}
