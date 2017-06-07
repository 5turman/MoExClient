package org.example.moex.core

import java.io.BufferedReader
import java.io.Closeable
import java.io.File
import java.io.FileReader

/**
 * Created by 5turman on 6/6/2017.
 */
class QuotesReader(file: File) : Closeable {

    private val reader = BufferedReader(FileReader(file))

    private var value = 0.0
    private var timestamp = 0L

    fun moveToNext(): Boolean {
        val line = reader.readLine()
        if (line != null) {
            val index = line.indexOf(':')
            if (index != -1) {
                value = line.substring(0, index).toDouble()
                timestamp = line.substring(index + 1, line.length).toLong()
                return true
            }
        }
        return false
    }

    fun getValue() = value

    fun getTimestamp() = timestamp

    override fun close() {
        reader.close()
    }

}
