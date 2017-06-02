package org.example.moex.api.dto

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

/**
 * Created by 5turman on 6/1/2017.
 */
@Root(strict = false)
class CandlesDTO {

    @field:ElementList(name = "rows", entry = "row") @field:Path("data[1]")
    lateinit var candles: List<CandleDTO>

}
