package org.example.moex.api.dto

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

/**
 * Created by 5turman on 21.03.2017.
 */
@Root(strict = false)
class SharesDTO {

    @field:ElementList(name = "rows", entry = "row") @field:Path("data[1]")
    var securities = mutableListOf<ShareDTO>()

    @field:ElementList(name = "rows", entry = "row") @field:Path("data[2]")
    var marketData = mutableListOf<ShareDataDTO>()

}
