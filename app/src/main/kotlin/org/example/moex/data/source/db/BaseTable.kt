package org.example.moex.data.source.db

/**
 * Created by 5turman on 30.03.2017.
 */
abstract class BaseTable(val name: String) {

    abstract fun createStatement(): String

}
