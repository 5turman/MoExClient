package org.example.moex.api.dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

/**
 * Created by 5turman on 21.03.2017.
 */
@Root(strict = false)
class ShareDataDTO {
    /**
     * Instrument ID
     */
    @field:Attribute(name = "SECID") var id = ""
    /**
     * Trading Mode ID
     */
    @field:Attribute(name = "BOARDID") var boardId = ""
    /**
     * Time of Last Transaction
     */
    @field:Attribute(name = "TIME") var time = ""
    /**
     * Last Transaction Price, roubles
     */
    @field:Attribute(name = "LAST") var last = ""
    /**
     * Change of Last Transaction Price to Last Transaction Price of Previous Trading Day, percent
     */
    @field:Attribute(name = "LASTTOPREVPRICE") var lastToPrevPrice = 0.0
    /**
     * Data Upload time
     */
    @field:Attribute(name = "SYSTIME") var sysTime = ""
//
//    /**
//     * Time of Last Updating
//     */
//    @field:Attribute(name = "UPDATETIME") var updateTime = ""
//    /**
//     * Status
//     */
//    @field:Attribute(name = "TRADINGSTATUS") var status = ""
//    /**
//     * Market Price
//     */
//    @field:Attribute(name = "MARKETPRICETODAY") var marketPriceToday = ""
//    /**
//     * Market Price of Previous Trading Day
//     */
//    @field:Attribute(name = "MARKETPRICE") var marketPrice = 0.0
//    /**
//     * Spread, RUB
//     */
//    @field:Attribute(name = "SPREAD") var spread = 0.0
//    /**
//     * First Transaction Price (First Price), roubles
//     */
//    @field:Attribute(name = "OPEN") var open = ""
//    /**
//     * Price of Pre-trading Period
//     */
//    @field:Attribute(name = "OPENPERIODPRICE") var openPeriodPrice = ""
//    /**
//     * Minimum Price, roubles
//     */
//    @field:Attribute(name = "LOW") var low = ""
//    /**
//     * Maximum Price of Transaction, roubles
//     */
//    @field:Attribute(name = "HIGH") var high = ""
//    /**
//     * Change of Last Transaction Price to the Previous Transaction Price, RuB
//     */
//    @field:Attribute(name = "LASTCHANGE") var lastChange = 0.0
//    /**
//     * Change of Last Transaction Price to Previous Trading Day Price, percent
//     */
//    @field:Attribute(name = "LASTCHANGEPRCNT") var lastChangePercent = 0.0
//    /**
//     * Change of Last Transaction Price to WAP, roubles
//     */
//    @field:Attribute(name = "LASTCNGTOLASTWAPRICE") var lastChangeToLastWaPrice = 0.0
//    /**
//     * Current Price
//     */
//    @field:Attribute(name = "LCURRENTPRICE") var currentPrice = ""
//    /**
//     * Value of Last Transaction, lots
//     */
//    @field:Attribute(name = "QTY") var quantity = ""
//    /**
//     * Value of Last Transaction, RUB
//     */
//    @field:Attribute(name = "VALUE") var value = 0.0
//    /**
//     * Weighted Average Price, roubles
//     */
//    @field:Attribute(name = "WAPRICE") var waPrice = ""
//    /**
//     * Change of Weighted Average Price to Weighted Average Price of Previous Trading Day (Change of WAP to Previous Day WAP), roubles
//     */
//    @field:Attribute(name = "WAPTOPREVWAPRICE") var waToPrevWaPrice = 0.0
//    /**
//     * Change of Weighted Average Price to Weighted Average Price of Previous Trading Day (Change of WAP to Previous Day WAP), percent
//     */
//    @field:Attribute(name = "WAPTOPREVWAPRICEPRCNT") var waToPrevWaPricePercent = 0.0
//    /**
//     * Volume of Concluded Transactions in Securities, units
//     */
//    @field:Attribute(name = "VOLTODAY") var todayVolume = ""
//    /**
//     * Change of Last Transaction Price to Last Transaction Price of Previous Trading Day (Change of Last to Previous Day Last), RuB
//     */
//    @field:Attribute(name = "CHANGE") var change = ""
//    /**
//     * Quantity of Concluded Transactions, units
//     */
//    @field:Attribute(name = "NUMTRADES") var tradesNum = 0
//    /**
//     * Stock issue capitalization
//     */
//    @field:Attribute(name = "ISSUECAPITALIZATION") var capitalization = ""
}
