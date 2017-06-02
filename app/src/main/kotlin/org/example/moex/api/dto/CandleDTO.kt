package org.example.moex.api.dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

/**
 * Created by 5turman on 6/1/2017.
 */
@Root(strict = false)
class CandleDTO {

    @field:Attribute var open = 0.0
    @field:Attribute var close = 0.0
    @field:Attribute var high = 0.0
    @field:Attribute var low = 0.0
    @field:Attribute var value = 0.0
    @field:Attribute var volume = 0.0
    @field:Attribute var begin = ""
    @field:Attribute var end = ""

}