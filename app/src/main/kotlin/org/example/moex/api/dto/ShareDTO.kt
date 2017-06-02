package org.example.moex.api.dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

/**
 * Created by 5turman on 21.03.2017.
 */
@Root(strict = false)
class ShareDTO {
    @field:Attribute(name = "SECID") var id = "" // Код ценной бумаги
    @field:Attribute(name = "ISIN") var ISIN = "" // ISIN код
    @field:Attribute(name = "REGNUMBER") var regNumber = "" // Номер государственной регистрации

    @field:Attribute(name = "BOARDID") var boardId = "" // Код режима
    @field:Attribute(name = "BOARDNAME") var boardName = "" // Режим торгов

    @field:Attribute(name = "SECNAME") var name = "" // Полное наименование
    @field:Attribute(name = "SHORTNAME") var shortName = "" // Краткое наименование
    @field:Attribute(name = "LATNAME") var latName = "" // Имя на латинице

    @field:Attribute(name = "INSTRID") var instrumentId = "" // Группа инструментов
    @field:Attribute(name = "SECTORID") var sectorId = "" // Сектор

    @field:Attribute(name = "STATUS") var status = ""

    /**
     * Outstanding Part of Basic Debt (Номинальная стоимость)
     */
    @field:Attribute(name = "FACEVALUE") var faceValue = 0.0
    @field:Attribute(name = "ISSUESIZE") var issueSize = "" // Объем выпуска
    @field:Attribute(name = "LOTSIZE") var lotSize = 0 // Размер лота
    @field:Attribute(name = "CURRENCYID") var currency = ""

}
