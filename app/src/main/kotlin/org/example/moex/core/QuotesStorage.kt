package org.example.moex.core

import java.io.File

/**
 * Created by 5turman on 6/6/2017.
 */
class QuotesStorage(private val dir: File) {

    fun getReader(shareId: String): QuotesReader = QuotesReader(getFile(shareId))

    fun getWriter(shareId: String): QuotesWriter = QuotesWriter(getFile(shareId))

    private fun getFile(shareId: String) = File(dir, "$shareId.csv")

}
