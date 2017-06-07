package org.example.moex.core

import java.io.Closeable
import java.io.File
import java.io.FileWriter

/**
 * Created by 5turman on 6/6/2017.
 */
class QuotesWriter(file: File) : Closeable {

    private val fileWriter = FileWriter(file, true)

    fun write(value: Double, timestamp: Long) : QuotesWriter {
        fileWriter.write("$value:$timestamp\n")
        return this
    }

    override fun close() {
        fileWriter.close()
    }

}
